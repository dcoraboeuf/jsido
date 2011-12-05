package net.sf.sido.schema;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class SidoSchema {

	private final String uri;
	private final List<SidoPrefix> prefixes;

	public SidoSchema(String uri, List<SidoPrefix> prefixes) {
		this.uri = uri;
		this.prefixes = ImmutableList.copyOf(prefixes);
	}

	public String getUri() {
		return uri;
	}

	public List<SidoPrefix> getPrefixes() {
		return prefixes;
	}

}
