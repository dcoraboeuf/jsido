package net.sf.sido.gen.model.support;

import net.sf.sido.gen.model.Options;

public abstract class AbstractOptions implements Options {

	@Override
	public boolean getBoolean(String name, boolean defaultValue) {
		String value = getString(name, null);
		if (value == null) {
			return defaultValue;
		} else {
			return Boolean.parseBoolean(value);
		}
	}

	@Override
	public int getInt(String name, int defaultValue) {
		String value = getString(name, null);
		if (value == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(value);
		}
	}

	@Override
	public long getLong(String name, long defaultValue) {
		String value = getString(name, null);
		if (value == null) {
			return defaultValue;
		} else {
			return Long.parseLong(value);
		}
	}

}
