package net.sf.sido.schema;

import static org.junit.Assert.assertTrue;
import net.sf.sido.schema.support.DefaultSidoContext;

import org.junit.Before;
import org.junit.Test;

public class SidoTest {
	
	@Before
	public void before() {
		Sido.setContext(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void default_null() {
		Sido.setDefault(null);
	}
	
	@Test
	public void default_set() {
		DefaultSidoContext context = new DefaultSidoContext();
		Sido.setDefault(context);
		assertTrue (context == Sido.getContext());
	}
	
	@Test
	public void context() {
		SidoContext old = Sido.getContext();
		DefaultSidoContext context = new DefaultSidoContext();
		Sido.setContext(context);
		assertTrue (context == Sido.getContext());
		Sido.setContext(null);
		assertTrue (old == Sido.getContext());
	}

}
