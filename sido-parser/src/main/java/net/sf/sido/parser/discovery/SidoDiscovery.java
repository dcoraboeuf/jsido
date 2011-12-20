package net.sf.sido.parser.discovery;

import java.util.Collection;

import net.sf.sido.schema.SidoContext;

public interface SidoDiscovery {

	String SIDO_ENCODING = "UTF-8";
	
	Collection<SidoSchemaDiscovery> discover(SidoContext context, SidoDiscoveryLogger logger);

}
