package net.sf.sido.schema.support;

public class SidoTypeNotFoundException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoTypeNotFoundException(String qualifiedName) {
		super(qualifiedName);
	}

}
