package net.sf.sido.parser.support;

import net.sf.sido.schema.support.SidoException;

public class SidoParseExceptionDetail extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoParseExceptionDetail(String match, int line, int startIndex, int endIndex,
			String errorMessage) {
		super(match, line, startIndex, endIndex, errorMessage);
	}

}
