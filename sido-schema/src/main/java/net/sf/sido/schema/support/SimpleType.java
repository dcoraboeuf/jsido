package net.sf.sido.schema.support;

import java.math.BigDecimal;

public class SimpleType<T> extends AbstractSidoSimpleType<T> {
	
	@SuppressWarnings("unchecked")
	public static <T> T getDefaultValue(Class<T> type) {
		if (Boolean.class.isAssignableFrom(type)) {
			return (T) Boolean.FALSE;
		} else if (String.class.isAssignableFrom(type)) {
			return (T) "";
		} else if (Byte.class.isAssignableFrom(type)) {
			return (T) Byte.valueOf((byte) 0);
		} else if (Short.class.isAssignableFrom(type)) {
			return (T) Short.valueOf((short) 0);
		} else if (Integer.class.isAssignableFrom(type)) {
			return (T) Integer.valueOf(0);
		} else if (Long.class.isAssignableFrom(type)) {
			return (T) Long.valueOf(0);
		} else if (Float.class.isAssignableFrom(type)) {
			return (T) Float.valueOf(0);
		} else if (Double.class.isAssignableFrom(type)) {
			return (T) Double.valueOf(0);
		} else if (BigDecimal.class.isAssignableFrom(type)) {
			return (T) BigDecimal.ZERO;
		} else {
			return null;
		}
	}

	public static <T> SimpleType<T> get(String name, Class<T> type) {
		return new SimpleType<T>(name, type);
	}

	private final Class<T> type;

	public SimpleType(String name, Class<T> type) {
		super(name);
		this.type = type;
	}

	@Override
	public Class<T> getType() {
		return type;
	}
	
	@Override
	public T getDefaultValue() {
		return getDefaultValue(type);
	}

}
