package net.sf.sido.parser.discovery.support;

import java.io.IOException;

import net.sf.sido.schema.support.SidoException;

public class SidoDiscoveryException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoDiscoveryException(IOException ex) {
		super(ex, ex);
	}

}
