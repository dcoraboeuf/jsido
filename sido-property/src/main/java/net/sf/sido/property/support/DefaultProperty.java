package net.sf.sido.property.support;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import net.sf.sido.property.LockedPropertyException;

public class DefaultProperty<T> extends AbstractProperty<T> {

	private final AtomicReference<T> value;
	private final AtomicBoolean locked = new AtomicBoolean(false);

	public DefaultProperty(String name) {
		super(name);
		this.value = new AtomicReference<T>();
	}

	public DefaultProperty(String name, T value) {
		super(name);
		this.value = new AtomicReference<T>(value);
	}

	@Override
	public T get() {
		return value.get();
	}

	public void set(T value) throws LockedPropertyException {
		if (locked.get()) {
			throw new LockedPropertyException();
		} else {
			this.value.set(value);
		}
	}

	@Override
	public boolean isLocked() {
		return locked.get();
	}

	public void lock() {
		locked.set(true);
	}

	@Override
	public String toString() {
		return String.format("%s = %s", name, value);
	}

}
