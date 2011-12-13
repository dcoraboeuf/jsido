package net.sf.sido.schema.model;

public abstract class AbstractSidoItem {
	
	@SuppressWarnings("unused")
	private boolean closed;
	
	public void close() {
		closed = true;
	}

}
