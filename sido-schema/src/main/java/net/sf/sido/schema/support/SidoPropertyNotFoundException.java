package net.sf.sido.schema.support;

public class SidoPropertyNotFoundException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoPropertyNotFoundException(String qualifiedName, String name) {
		super(qualifiedName, name);
	}

}
