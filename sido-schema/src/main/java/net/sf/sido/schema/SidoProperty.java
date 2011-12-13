package net.sf.sido.schema;

public interface SidoProperty {
	
	String getName();
	
	boolean isCollection();
	
	String getCollectionIndex();
	
	boolean isNullable();

}
