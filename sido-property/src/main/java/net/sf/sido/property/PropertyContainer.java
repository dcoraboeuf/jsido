package net.sf.sido.property;

import java.util.Collection;

public interface PropertyContainer {
	
	Collection<? extends Property<?>> getProperties();
	
	void lock();
	
	boolean isLocked();

}
