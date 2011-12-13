package net.sf.sido.schema.builder;

import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.model.DefaultSidoSchema;
import net.sf.sido.schema.model.DefaultSidoType;

public class DefaultSidoTypeBuilder implements SidoTypeBuilder {

	private final DefaultSidoType type;

	public DefaultSidoTypeBuilder(DefaultSidoSchema schema, String name) {
		this.type = new DefaultSidoType(schema, name);
	}

	@Override
	public SidoTypeBuilder setParentType(SidoType parentType) {
		type.setParentType(parentType);
		return this;
	}
	
	@Override
	public SidoType getType() {
		return type;
	}
	
	@Override
	public void close() {
		type.close();
	}

}
