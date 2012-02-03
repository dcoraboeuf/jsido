package net.sf.sido.schema.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DefaultSidoAnonymousPropertyTest {
	
	@Test(expected = NullPointerException.class)
	public void constructor_no_name() {
		new DefaultSidoAnonymousProperty(null, false, false);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructor_blank_name() {
		new DefaultSidoAnonymousProperty(" ", false, false);
	}
	
	@Test
	public void constructor() {
		DefaultSidoAnonymousProperty p = new DefaultSidoAnonymousProperty("name", false, false);
		assertEquals("name", p.getName());
		assertFalse(p.isNullable());
		assertFalse(p.isCollection());
		assertNull(p.getCollectionIndex());
	}
	
	@Test
	public void constructor_nullable() {
		DefaultSidoAnonymousProperty p = new DefaultSidoAnonymousProperty("name", true, false);
		assertEquals("name", p.getName());
		assertTrue(p.isNullable());
		assertFalse(p.isCollection());
		assertNull(p.getCollectionIndex());
	}
	
	@Test
	public void constructor_collection() {
		DefaultSidoAnonymousProperty p = new DefaultSidoAnonymousProperty("name", false, true);
		assertEquals("name", p.getName());
		assertFalse(p.isNullable());
		assertTrue(p.isCollection());
		assertNull(p.getCollectionIndex());
	}

}
