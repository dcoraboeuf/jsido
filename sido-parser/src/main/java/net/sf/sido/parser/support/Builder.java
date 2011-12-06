package net.sf.sido.parser.support;

import net.sf.sido.parser.model.XSchema;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;

public class Builder {

	public static Builder create(SidoContext context) {
		return new Builder(context);
	}
	
	private final SidoContext context;

	public Builder(SidoContext context) {
		this.context = context;
	}

	public SidoSchema build(XSchema xSchema) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
