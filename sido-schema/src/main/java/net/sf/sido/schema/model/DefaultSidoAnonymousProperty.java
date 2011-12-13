package net.sf.sido.schema.model;

import net.sf.sido.schema.SidoAnonymousProperty;

public class DefaultSidoAnonymousProperty extends AbstractSidoProperty implements SidoAnonymousProperty {

	public DefaultSidoAnonymousProperty(String name, boolean nullable, boolean collection) {
		super(name, nullable, collection);
	}

}
