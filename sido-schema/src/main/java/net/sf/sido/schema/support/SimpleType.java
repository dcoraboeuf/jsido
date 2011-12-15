package net.sf.sido.schema.support;

public class SimpleType<T> extends AbstractSidoSimpleType<T> {

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

}
