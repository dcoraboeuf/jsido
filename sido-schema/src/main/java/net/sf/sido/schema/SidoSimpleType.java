package net.sf.sido.schema;

public interface SidoSimpleType<T> {

	String getName();

	Class<T> getType();

	T getDefaultValue();
	
	String getDefaultJavaInitialization();

}
