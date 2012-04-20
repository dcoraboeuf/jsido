package net.sf.sido.schema;

import org.apache.commons.lang3.Validate;

import net.sf.sido.schema.support.DefaultSidoContext;

public final class Sido {
	
	private static SidoContext DEFAULT = new DefaultSidoContext();
	
	private static final ThreadLocal<SidoContext> CONTEXT = new ThreadLocal<SidoContext>();
	
	public static void setDefault (SidoContext context) {
		Validate.notNull(context);
		DEFAULT = context;
	}
	
	public static SidoContext setContext (SidoContext context) {
		SidoContext old = CONTEXT.get();
		CONTEXT.set(context);
		return old;
	}
	
	public static SidoContext getContext () {
		SidoContext context = CONTEXT.get();
		if (context != null) {
			return context;
		} else {
			return DEFAULT;
		}
	}

}
