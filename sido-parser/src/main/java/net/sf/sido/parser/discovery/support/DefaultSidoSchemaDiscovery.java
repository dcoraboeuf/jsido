package net.sf.sido.parser.discovery.support;

import net.sf.sido.parser.discovery.SidoSchemaDiscovery;

public class DefaultSidoSchemaDiscovery implements SidoSchemaDiscovery {

	private final String uid;

	public DefaultSidoSchemaDiscovery(String uid) {
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return uid;
	}
	
	@Override
	public String toString() {
		return uid;
	}

}
