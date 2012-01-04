package net.sf.sido.gen.model;

public interface Options {

	boolean getBoolean (String name, boolean defaultValue);
	
	int getInt (String name, int defaultValue);
	
	long getLong (String name, long defaultValue);
	
	String getString (String name, String defaultValue);

}
