package net.sf.sido.schema.builder;

import net.sf.sido.schema.SidoType;

public interface SidoTypeBuilder {

	SidoTypeBuilder setParentType(SidoType parentType);

	SidoType getType();

	void close();

}
