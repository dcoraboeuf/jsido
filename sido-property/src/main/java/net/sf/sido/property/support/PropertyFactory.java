package net.sf.sido.property.support;

import net.sf.sido.property.Property;

public class PropertyFactory {

	public static <T> Property<T> mutable(String name) {
		return mutable(name, null);
	}

	public static <T> Property<T> mutable(String name, T value) {
		return new DefaultProperty<T>(name, value);
	}

	public static <T> Property<T> readOnly(String name, T value) {
		return new ReadOnlyProperty<T>(name, value);
	}

}
