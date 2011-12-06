package net.sf.sido.schema.support;

public class SidoSchemaUIDDuplicationException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoSchemaUIDDuplicationException(String uid) {
		super(uid);
	}

}
