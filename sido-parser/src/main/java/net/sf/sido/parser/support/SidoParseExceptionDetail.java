package net.sf.sido.parser.support;

import net.sf.sido.schema.support.SidoException;

public class SidoParseExceptionDetail extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoParseExceptionDetail(int startIndex, int endIndex,
			String errorMessage) {
		super(startIndex, endIndex, errorMessage);
	}

}
