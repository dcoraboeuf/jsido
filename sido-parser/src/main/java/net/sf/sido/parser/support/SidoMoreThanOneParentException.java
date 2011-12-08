package net.sf.sido.parser.support;

import net.sf.sido.schema.support.SidoException;

public class SidoMoreThanOneParentException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoMoreThanOneParentException(String name) {
		super(name);
	}

}
