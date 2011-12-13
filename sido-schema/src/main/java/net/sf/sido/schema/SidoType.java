package net.sf.sido.schema;

public interface SidoType {
	
	SidoSchema getSchema();
	
	String getName();

	SidoType getParentType();

}
