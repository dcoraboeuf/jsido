package net.sf.sido.schema.builder;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.model.DefaultSidoSchema;

public class SchemaBuilder {

	public static SchemaBuilder create(SidoContext context, String uid) {
		return new SchemaBuilder(context, uid);
	}
	
	private final DefaultSidoSchema schema;

	public SchemaBuilder(SidoContext context, String uid) {
		schema = new DefaultSidoSchema(context, uid);
	}

	public SidoSchema build() {
		schema.lock();
		return schema;
	}

}
