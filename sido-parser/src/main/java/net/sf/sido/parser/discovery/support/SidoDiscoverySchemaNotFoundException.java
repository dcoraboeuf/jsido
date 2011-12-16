package net.sf.sido.parser.discovery.support;

import net.sf.sido.schema.support.SidoException;

public class SidoDiscoverySchemaNotFoundException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoDiscoverySchemaNotFoundException(String schemaPath) {
		super(schemaPath);
	}

}
