package net.sf.sido.schema.builder;

import net.sf.sido.schema.SidoContext;

public class SidoSchemaBuilderFactory {
	
	public static SidoSchemaBuilderFactory newInstance(SidoContext context) {
		return new SidoSchemaBuilderFactory(context);
	}

	private final SidoContext context;
	
	protected SidoSchemaBuilderFactory(SidoContext context) {
		this.context = context;
	}

	public SidoSchemaBuilder create(String schemaUid) {
		return new DefaultSidoSchemaBuilder(context, schemaUid);
	}

}
