package net.sf.sido.property;

public interface Property<T> {
	
	String getName();
	
	T get();
	
	void set (T value) throws LockedPropertyException;
	
	boolean isLocked();
	
	void lock();

}
