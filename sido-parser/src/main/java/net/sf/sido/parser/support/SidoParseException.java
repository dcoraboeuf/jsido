package net.sf.sido.parser.support;

import net.sf.jstring.Localizable;
import net.sf.sido.schema.support.SidoException;

public class SidoParseException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoParseException(Localizable error) {
		super(error);
	}

}
