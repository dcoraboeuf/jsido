package net.sf.sido.property.support;

import net.sf.sido.property.Property;

public abstract class AbstractProperty<T> implements Property<T> {

	protected final String name;

	public AbstractProperty(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
