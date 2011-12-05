package net.sf.sido.schema;

public class SidoPrefix {

	private final String prefix;
	private final String uri;

	public SidoPrefix(String prefix, String uri) {
		this.prefix = prefix;
		this.uri = uri;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getUri() {
		return uri;
	}

}
