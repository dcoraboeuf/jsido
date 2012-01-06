package net.sf.sido.gen.cli;

public class CLIException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public CLIException(String pattern, Object... parameters) {
		super(String.format(pattern, parameters));
	}

}
