package net.sf.sido.property.support;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import net.sf.sido.property.Property;
import net.sf.sido.property.PropertyContainer;

public abstract class AbstractPropertyContainer implements PropertyContainer {

	private final AtomicBoolean locked = new AtomicBoolean(false);

	@Override
	public void lock() {
		if (locked.compareAndSet(false, true)) {
			doLock();
		}
	}

	protected void doLock() {
		Collection<? extends Property<?>> properties = getProperties();
		for (Property<?> property : properties) {
			property.lock();
		}
	}

	@Override
	public boolean isLocked() {
		return locked.get();
	}

}
