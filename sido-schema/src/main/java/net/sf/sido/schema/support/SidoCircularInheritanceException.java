package net.sf.sido.schema.support;

public class SidoCircularInheritanceException extends SidoException {

	private static final long serialVersionUID = 1L;

	public SidoCircularInheritanceException(String qualifiedName) {
		super(qualifiedName);
	}

}
