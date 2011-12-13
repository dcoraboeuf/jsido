package net.sf.sido.schema.model;

import net.sf.sido.schema.support.SideClosedItemException;

public abstract class AbstractSidoItem {
	
	private boolean closed;
	
	public void close() {
		closed = true;
	}
	
	protected void checkNotClosed() {
		if (closed) {
			throw new SideClosedItemException();
		}
	}

}
