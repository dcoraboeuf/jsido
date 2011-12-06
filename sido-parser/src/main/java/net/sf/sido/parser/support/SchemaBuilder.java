package net.sf.sido.parser.support;

import net.sf.sido.parser.model.XSchema;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;

public class SchemaBuilder {

	public static SchemaBuilder create(SidoContext context) {
		return new SchemaBuilder(context);
	}
	
	private final SidoContext context;

	public SchemaBuilder(SidoContext context) {
		this.context = context;
	}

	public SidoSchema build(XSchema xSchema) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
