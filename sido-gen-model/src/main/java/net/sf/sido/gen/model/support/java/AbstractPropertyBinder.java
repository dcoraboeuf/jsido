package net.sf.sido.gen.model.support.java;

import net.sf.sido.schema.SidoProperty;

public abstract class AbstractPropertyBinder<T extends SidoProperty> implements PropertyBinder<T> {

	protected String valueToString(Object value) {
		if (value instanceof String) {
			return "\"" + value + "\"";
		} else if (value != null) {
			return value.toString();
		} else {
			return "null";
		}
	}
}
