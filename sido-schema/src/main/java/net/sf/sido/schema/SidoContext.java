package net.sf.sido.schema;

import java.util.Collection;

public interface SidoContext {
	
	Collection<SidoSchema> getSchemas();
	
	SidoSchema getSchema (String uid, boolean required);

}
