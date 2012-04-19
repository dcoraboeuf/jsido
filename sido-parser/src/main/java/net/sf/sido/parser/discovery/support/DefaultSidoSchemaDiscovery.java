package net.sf.sido.parser.discovery.support;

import net.sf.sido.parser.discovery.SidoSchemaDiscovery;

public class DefaultSidoSchemaDiscovery implements SidoSchemaDiscovery {

	private final String uid;
	private final String model;

	public DefaultSidoSchemaDiscovery(String uid, String model) {
		this.uid = uid;
		this.model = model;
	}

	@Override
	public String getUid() {
		return uid;
	}
	
	@Override
	public String getModel() {
		return model;
	}
	
	@Override
	public String toString() {
		return String.format("%s model=%s", uid, model);
	}

}
