package net.sf.sido.schema;

import java.util.List;

public interface SidoSchema {
	
	String getUri();
	
	List<SidoPrefix> getPrefixes();

}
