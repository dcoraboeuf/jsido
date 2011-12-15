package net.sf.sido.schema.support;

import net.sf.sido.schema.SidoSimpleType;

public abstract class AbstractSidoSimpleType<T> implements SidoSimpleType<T> {

	private final String name;

	public AbstractSidoSimpleType(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
