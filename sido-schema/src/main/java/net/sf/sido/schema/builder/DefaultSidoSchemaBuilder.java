package net.sf.sido.schema.builder;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.model.DefaultSidoSchema;

public class DefaultSidoSchemaBuilder implements SidoSchemaBuilder {

	private final DefaultSidoSchema schema;

	public DefaultSidoSchemaBuilder(SidoContext context, String schemaUid) {
		// Creates the schema
		schema = new DefaultSidoSchema(context, schemaUid);
		// Registers the schema
		context.registerSchema(schema);
	}

	@Override
	public SidoSchema getSchema() {
		return schema;
	}

	@Override
	public void close() {
		schema.close();
	}

	@Override
	public String getUid() {
		return schema.getUid();
	}
	
	@Override
	public SidoTypeBuilder newType(String name) {
		DefaultSidoTypeBuilder builder = new DefaultSidoTypeBuilder(schema, name);
		// TODO The type is not registered in the schema yet
		return builder;
	}

}
