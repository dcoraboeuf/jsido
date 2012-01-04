package net.sf.sido.gen.model.support;

import java.util.Map;

public class MapOptions extends AbstractOptions {

	private final Map<String, String> values;

	public MapOptions(Map<String, String> values) {
		this.values = values;
	}

	@Override
	public String getString(String name, String defaultValue) {
		String value = values.get(name);
		return value != null ? value : defaultValue;
	}

}
