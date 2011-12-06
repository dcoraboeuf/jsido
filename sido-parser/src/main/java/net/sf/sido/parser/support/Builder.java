package net.sf.sido.parser.support;

import java.util.Collection;

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

	public Collection<SidoSchema> build(Collection<XSchema> xSchemas) {
		return null;
	}
	
}
