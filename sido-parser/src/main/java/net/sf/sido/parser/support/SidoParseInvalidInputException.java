package net.sf.sido.parser.support;

import net.sf.jstring.Localizable;
import net.sf.sido.schema.support.SidoException;

public class SidoParseInvalidInputException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoParseInvalidInputException(String match, int line, int startIndex, int endIndex, Localizable failedMatchers) {
		super(match, line, startIndex, endIndex, failedMatchers);
	}


}
