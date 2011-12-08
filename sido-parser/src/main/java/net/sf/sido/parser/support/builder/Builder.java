package net.sf.sido.parser.support.builder;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.sido.parser.model.XSchema;
import net.sf.sido.parser.model.XType;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
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
		// TODO Second pass: resolution of types
		// Third pass: closes all schemas
		for (SidoSchema schema : schemas.values()) {
			schema.close();
		}
		// OK
		return schemas.values();
	}
	
}
