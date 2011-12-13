package net.sf.sido.schema.model;

import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoSimpleType;

public class DefaultSidoSimpleProperty<P> extends AbstractSidoProperty implements SidoSimpleProperty<P> {

	private final SidoSimpleType<P> type;

	public DefaultSidoSimpleProperty(String name, SidoSimpleType<P> type, boolean nullable, boolean collection) {
		super(name, nullable, collection, null);
		this.type = type;
	}

	@Override
	public SidoSimpleType<P> getType() {
		return type;
	}

}
