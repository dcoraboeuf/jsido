package net.sf.sido.parser.support;

import net.sf.sido.parser.model.XSchema;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.builder.SchemaBuilder;

public class Builder {

	public static Builder create(SidoContext context) {
		return new Builder(context);
	}
	
	private final SidoContext context;

	public Builder(SidoContext context) {
		this.context = context;
	}

	public SidoSchema build(XSchema xSchema) {
		// Creates a builder
		SchemaBuilder schemaBuilder = SchemaBuilder.create(context, xSchema.getUid());
		// TODO Auto-generated method stub
		// OK
		return schemaBuilder.build();
	}
	
}
