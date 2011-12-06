package net.sf.sido.schema;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class SidoSchema {

	private final String uri;
	private final Map<String, SidoPrefix> prefixes;
	private final Map<String, SidoType> types;

	public SidoSchema(String uri, List<SidoPrefix> prefixes, List<SidoType> types) {
		this.uri = uri;
		this.prefixes = Maps.uniqueIndex(prefixes, new Function<SidoPrefix, String>() {
			@Override
			public String apply(SidoPrefix o) {
				return o.getPrefix();
			}
		});
		this.types = Maps.uniqueIndex(types, new Function<SidoType, String>() {
			@Override
			public String apply(SidoType o) {
				return o.getName();
			}
		});
	}

	public String getUri() {
		return uri;
	}

	public Map<String, SidoPrefix> getPrefixes() {
		return prefixes;
	}
	
	public Map<String, SidoType> getTypes() {
		return types;
	}

}
