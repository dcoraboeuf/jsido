package net.sf.sido.parser.support.builder;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sf.sido.parser.model.XSchema;
import net.sf.sido.parser.model.XType;
import net.sf.sido.parser.model.XTypeRef;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.support.SidoSchemaUIDDuplicationException;

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
		Map<String, SidoSchema> schemas = new LinkedHashMap<String, SidoSchema>();
		// First pass: indexation of types and schemas
		for (XSchema xSchema : xSchemas) {
			String schemaUid = xSchema.getUid();
			// Checks if the schema is already defined
			if (context.getSchema(schemaUid, false) != null) {
				throw new SidoSchemaUIDDuplicationException(schemaUid);
			}
			// Creates the schema
			SidoSchema schema = context.createSchema(schemaUid);
			// Indexes the schema
			schemas.put(schemaUid, schema);
			// All types in this schema
			for (XType xType : xSchema.getTypes()) {
				// Creates the type definition
				TypeDefinition definition = new TypeDefinition(schema, xType);
				// Indexes the type
				typeDefinitions.put(definition.getQualifiedName(), definition);
			}
		}
		// Second pass: resolution of types
		resolve(schemas, typeDefinitions);
		// Third pass: closes all schemas
		for (SidoSchema schema : schemas.values()) {
			schema.close();
		}
		// OK
		return schemas.values();
	}

	protected void resolve(Map<String, SidoSchema> schemas, Map<String, TypeDefinition> typeDefinitions) {
		for (TypeDefinition typeDefinition : typeDefinitions.values()) {
			ResolutionStatus status = typeDefinition.getStatus();
			if (status == ResolutionStatus.PENDING) {
				readType(schemas, typeDefinitions, typeDefinition);
			} else if (status != ResolutionStatus.COMPLETE) {
				throw new IllegalStateException(String.format(
						"Status %s for definition %s cannot be resolved.",
							status,
							typeDefinition.getQualifiedName()));
			}
		}
	}

	protected void readType(Map<String, SidoSchema> schemas, Map<String, TypeDefinition> typeDefinitions,
			TypeDefinition definition) {
		// FIXME Resolution of a type
		// definition.start();
		// // Super-type
		// XTypeRef parent = definition.getXType().getParent();
		// if (parent != null) {
		// // Gets the parent type reference
		// SidoType parentType = getDOType(schemas, typeDefinitions, parent);
		// // Sets as parent
		// definition.setParentType(parentType);
		// }
		// // Gets all properties
		// Array<DataObject> properties = definition.getProperties();
		// for (DataObject property : properties) {
		// readProperty(factory, schemas, typeDefinitions, definition,
		// property);
		// }
		// // Completion
		// definition.complete();
	}

}
