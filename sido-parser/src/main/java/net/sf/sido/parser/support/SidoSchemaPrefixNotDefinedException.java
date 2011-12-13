package net.sf.sido.parser.support;

import net.sf.sido.schema.support.SidoException;

public class SidoSchemaPrefixNotDefinedException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoSchemaPrefixNotDefinedException(String prefix, String name) {
		super(prefix, name);
	}

}
