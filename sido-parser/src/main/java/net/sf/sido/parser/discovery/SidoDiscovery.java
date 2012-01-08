package net.sf.sido.parser.discovery;

import java.util.Collection;

import net.sf.sido.schema.SidoContext;

public interface SidoDiscovery {

	String SIDO_ENCODING = "UTF-8";

	String SIDO_SCHEMAS = "META-INF/sido/sido.schemas";

	String SIDO_SCHEMA_PATH = "META-INF/sido/%s";
	
	Collection<SidoSchemaDiscovery> discover(SidoContext context, SidoDiscoveryLogger logger);

}
