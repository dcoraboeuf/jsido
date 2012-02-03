package net.sf.sido.schema.model;

import org.apache.commons.lang3.Validate;

import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoSimpleType;

public class DefaultSidoSimpleProperty<P> extends AbstractSidoProperty implements SidoSimpleProperty<P> {

	private final SidoSimpleType<P> type;

	public DefaultSidoSimpleProperty(String name, SidoSimpleType<P> type, boolean nullable, boolean collection) {
		super(name, nullable, collection, null);
		Validate.notNull(type, "Simple type is required");
		this.type = type;
	}

	@Override
	public SidoSimpleType<P> getType() {
		return type;
	}

}
