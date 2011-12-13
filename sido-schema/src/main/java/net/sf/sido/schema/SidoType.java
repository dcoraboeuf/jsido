package net.sf.sido.schema;

import java.util.Collection;

public interface SidoType {
	
	SidoSchema getSchema();
	
	String getName();
	
	String getQualifiedName();

	SidoType getParentType();

	boolean isAbstractType();
	
	Collection<? extends SidoProperty> getProperties();
	
	<P extends SidoProperty> P getProperty (String name, boolean required);

}
