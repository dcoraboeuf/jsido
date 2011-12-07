package net.sf.sido.schema.model;

import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;

public class DefaultSidoType extends AbstractSidoItem implements SidoType {

	private final String name;
	private SidoSchema schema;

	public DefaultSidoType(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public SidoSchema getSchema() {
		return schema;
	}

	public void setSchema(SidoSchema schema) {
		this.schema =schema;
	}

}
