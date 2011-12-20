package net.sf.sido.parser.discovery;

import java.util.Collection;

import net.sf.sido.schema.SidoContext;

public interface SidoDiscovery {
	
	Collection<SidoSchemaDiscovery> discover(SidoContext context, SidoDiscoveryLogger logger);

}
