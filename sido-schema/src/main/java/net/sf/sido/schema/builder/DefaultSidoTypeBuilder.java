package net.sf.sido.schema.builder;

import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.model.DefaultSidoType;

public class DefaultSidoTypeBuilder implements SidoTypeBuilder {

	private final SidoSchemaBuilder schemaBuilder;
	private final DefaultSidoType type;

	public DefaultSidoTypeBuilder(SidoSchemaBuilder schemaBuilder, String name) {
		this.schemaBuilder = schemaBuilder;
		this.type = new DefaultSidoType(schemaBuilder.getSchema(), name);
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
		schemaBuilder.close(this);
	}

}
