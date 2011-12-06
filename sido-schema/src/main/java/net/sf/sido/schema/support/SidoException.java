package net.sf.sido.schema.support;

import net.sf.jstring.LocalizableException;

public abstract class SidoException extends LocalizableException {

	private static final long serialVersionUID = 1L;

	public SidoException(Object... params) {
		super(SidoException.class.getName(), params);
	}

	public SidoException(Throwable error, Object... params) {
		super(SidoException.class.getName(), error, params);
	}

	public SidoException() {
		super(SidoException.class.getName());
	}
	
	@Override
	public String getCode() {
		return getClass().getName();
	}

}
