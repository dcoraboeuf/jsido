package net.sf.sido.schema.support;

public class SidoWrongQualifiedNameException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoWrongQualifiedNameException(String qualifiedName) {
		super(qualifiedName);
	}

}
