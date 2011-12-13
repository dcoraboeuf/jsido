package net.sf.sido.schema;

public interface SidoProperty<P> {
	
	String getName();
	
	Class<P> getType();
	
	boolean isCollection();
	
	String getCollectionIndex();
	
	boolean isNullable();

}
