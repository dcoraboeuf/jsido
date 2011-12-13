package net.sf.sido.schema.model;

import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoType;

public class DefaultSidoRefProperty extends AbstractSidoProperty implements SidoRefProperty {

	private final SidoType type;

	public DefaultSidoRefProperty(String name, SidoType type, boolean nullable, boolean collection,
			String collectionIndex) {
		super(name, nullable, collection, collectionIndex);
		this.type = type;
	}

	@Override
	public SidoType getType() {
		return type;
	}

}
