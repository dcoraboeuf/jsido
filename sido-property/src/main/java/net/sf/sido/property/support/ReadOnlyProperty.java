package net.sf.sido.property.support;

import net.sf.sido.property.LockedPropertyException;

public class ReadOnlyProperty<T> extends AbstractProperty<T> {

	private final T value;

	public ReadOnlyProperty(String name, T value) {
		super(name);
		this.value = value;
	}

	@Override
	public T get() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("%s = %s", name, value);
	}

	@Override
	public boolean isLocked() {
		return true;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void lock() {
	}

	public void set(T value) throws LockedPropertyException {
		throw new LockedPropertyException();
	}

}
