package net.sf.sido.parser.discovery.support;

import java.io.IOException;

import net.sf.sido.schema.support.SidoException;

public class SidoDiscoverySchemaIOException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoDiscoverySchemaIOException(String schemaPath, IOException ex) {
		super(ex, schemaPath, ex);
	}

}
