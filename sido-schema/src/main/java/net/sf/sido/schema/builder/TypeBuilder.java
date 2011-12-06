package net.sf.sido.schema.builder;

import net.sf.sido.schema.SidoType;

public class TypeBuilder {

	public static TypeBuilder create(String name) {
		return new TypeBuilder(name);
	}

	private final String name;

	public TypeBuilder(String name) {
		this.name = name;
	}

	public SidoType build() {
		return new SidoType(name);
	}

}
