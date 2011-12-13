package net.sf.sido.schema;

import java.util.Collection;

public interface SidoSchema {
	
	SidoContext getContext();

	String getUid();

	Collection<SidoType> getTypes();

	SidoType getType(String name, boolean required);

}
