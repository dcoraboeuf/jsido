package net.sf.sido.schema.support;

public class SidoTypeNotFoundInSchemaException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoTypeNotFoundInSchemaException(String uid, String name) {
		super(uid, name);
	}

}
