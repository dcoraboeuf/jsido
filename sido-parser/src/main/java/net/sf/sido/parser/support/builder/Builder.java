package net.sf.sido.parser.support.builder;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import net.sf.sido.parser.model.XProperty;
import net.sf.sido.parser.model.XPropertyTypeRef;
import net.sf.sido.parser.model.XSchema;
import net.sf.sido.parser.model.XType;
import net.sf.sido.parser.model.XTypeRef;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoSimpleType;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.builder.SidoSchemaBuilder;
import net.sf.sido.schema.builder.SidoSchemaBuilderFactory;
import net.sf.sido.schema.support.SidoSchemaUIDDuplicationException;
import net.sf.sido.schema.support.SidoTypeNotFoundException;

public class Builder {

	public static Builder create(SidoContext context) {
		return new Builder(context);
	}

	private final SidoContext context;

	public Builder(SidoContext context) {
		this.context = context;
	}

	public Collection<SidoSchema> build(Collection<XSchema> xSchemas) {
		// Index of all types
		Map<String, TypeDefinition> typeDefinitions = new LinkedHashMap<String, TypeDefinition>();
		// Index of schemas
		Map<String, SidoSchemaBuilder> schemas = new LinkedHashMap<String, SidoSchemaBuilder>();
		// Schema factory
		SidoSchemaBuilderFactory schemaBuilderFactory = SidoSchemaBuilderFactory
				.newInstance(context);
		// First pass: indexation of types and schemas
		for (XSchema xSchema : xSchemas) {
			String schemaUid = xSchema.getUid();
			// Checks if the schema is already defined
			if (context.getSchema(schemaUid, false) != null) {
				throw new SidoSchemaUIDDuplicationException(schemaUid);
			}
			// Creates the schema
			SidoSchemaBuilder schemaBuilder = schemaBuilderFactory
					.create(schemaUid);
			// Indexes the schema
			schemas.put(schemaUid, schemaBuilder);
			// All types in this schema
			for (XType xType : xSchema.getTypes()) {
				// Creates the type definition
				TypeDefinition definition = new TypeDefinition(schemaBuilder,
						xSchema, xType);
				// Indexes the type
				typeDefinitions.put(definition.getQualifiedName(), definition);
			}
		}
		// Second pass: resolution of types
		resolve(schemas, typeDefinitions);
		// Third pass: closes all schemas
		for (SidoSchemaBuilder schemaBuilder : schemas.values()) {
			schemaBuilder.close();
		}
		// OK
		return Collections2.transform(schemas.values(),
				new Function<SidoSchemaBuilder, SidoSchema>() {

					@Override
					public SidoSchema apply(SidoSchemaBuilder o) {
						return o.getSchema();
					}

				});
	}

	protected void resolve(Map<String, SidoSchemaBuilder> schemas,
			Map<String, TypeDefinition> typeDefinitions) {
		for (TypeDefinition typeDefinition : typeDefinitions.values()) {
			ResolutionStatus status = typeDefinition.getStatus();
			if (status == ResolutionStatus.PENDING) {
				readType(schemas, typeDefinitions, typeDefinition);
			} else if (status != ResolutionStatus.COMPLETE) {
				throw new IllegalStateException(String.format(
						"Status %s for definition %s cannot be resolved.",
						status, typeDefinition.getQualifiedName()));
			}
		}
	}

	protected void readType(Map<String, SidoSchemaBuilder> schemas,
			Map<String, TypeDefinition> typeDefinitions,
			TypeDefinition definition) {
		definition.start();
		// Super-type
		XTypeRef parent = definition.getXType().getParent();
		if (parent != null) {
			// Gets the parent type reference
			SidoType parentType = getDOType(schemas, typeDefinitions,
					definition, parent);
			// Sets as parent
			definition.setParentType(parentType);
		}
		// Gets all properties
		List<XProperty> properties = definition.getXType().getProperties();
		for (XProperty xProperty : properties) {
			readProperty(schemas, typeDefinitions, definition, xProperty);
		}
		// Completion
		definition.complete();
	}

	protected <X> void readProperty(Map<String, SidoSchemaBuilder> schemas,
			Map<String, TypeDefinition> typeDefinitions,
			TypeDefinition definition, XProperty xProperty) {
		// Nature of the type
		XPropertyTypeRef xPropertyTypeRef = xProperty.getPropertyTypeRef();
		PropertyAssembler propertyAssembler;
		// Anonymous
		if (xPropertyTypeRef.isAnonymous()) {
			propertyAssembler = new AnonymousPropertyAssembler();
		} else {
			// Gets the reference
			XTypeRef xTypeRef = xPropertyTypeRef.getTypeRef();
			String token = xTypeRef.getToken();
			// Simple type?
			SidoSimpleType<X> simpleType = context.getSimpleType(token, false);
			if (simpleType != null) {
				propertyAssembler = new SimplePropertyAssembler<X>(simpleType);
			}
			// Must be a reference
			else {
				SidoType refType = getDOType(schemas, typeDefinitions,
						definition, xTypeRef);
				propertyAssembler = new RefPropertyAssembler(refType);
			}
		}
		// Creates the property
		propertyAssembler.create(definition, xProperty);
	}

	protected SidoType getDOType(Map<String, SidoSchemaBuilder> schemas,
			Map<String, TypeDefinition> typeDefinitions,
			TypeDefinition definition, XTypeRef typeRef) {
		String qualifiedTypeName = definition.resolveQualifiedName(typeRef);
		// This type can be found from the current context, not only in current
		// schemas
		SidoType type = context.getType(qualifiedTypeName, false);
		if (type != null) {
			return type;
		}
		// Gets the definition for this type
		TypeDefinition tDefinition = typeDefinitions.get(qualifiedTypeName);
		if (tDefinition == null) {
			throw new SidoTypeNotFoundException(qualifiedTypeName);
		}
		// If not resolved, resolve it
		ResolutionStatus status = tDefinition.getStatus();
		if (status == ResolutionStatus.COMPLETE) {
			return tDefinition.getType();
		} else if (status == ResolutionStatus.PENDING) {
			readType(schemas, typeDefinitions, tDefinition);
			return tDefinition.getType();
		} else if (status == ResolutionStatus.ONGOING) {
			return tDefinition.getType();
		} else {
			throw new IllegalStateException(String.format(
					"Status %s is unknown.", status));
		}
	}

}
