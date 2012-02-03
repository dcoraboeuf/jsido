package net.sf.sido.schema.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import net.sf.sido.schema.SidoType;

import org.junit.Test;

public class DefaultSidoRefPropertyTest {
	
	@Test(expected = NullPointerException.class)
	public void constructor_no_name() {
		new DefaultSidoRefProperty(null, null, false, false, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructor_blank_name() {
		new DefaultSidoRefProperty(" ", null, false, false, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void constructor_no_ref() {
		new DefaultSidoRefProperty("name", null, false, false, null);
	}
	
	@Test
	public void constructor() {
		SidoType ref = mock(SidoType.class);
		DefaultSidoRefProperty p = new DefaultSidoRefProperty("name", ref, false, false, null);
		assertEquals("name", p.getName());
		assertSame(ref, p.getType());
		assertFalse(p.isNullable());
		assertFalse(p.isCollection());
		assertNull(p.getCollectionIndex());
	}
	
	@Test
	public void constructor_nullable() {
		SidoType ref = mock(SidoType.class);
		DefaultSidoRefProperty p = new DefaultSidoRefProperty("name", ref, true, false, null);
		assertEquals("name", p.getName());
		assertSame(ref, p.getType());
		assertTrue(p.isNullable());
		assertFalse(p.isCollection());
		assertNull(p.getCollectionIndex());
	}
	
	@Test
	public void constructor_collection() {
		SidoType ref = mock(SidoType.class);
		DefaultSidoRefProperty p = new DefaultSidoRefProperty("name", ref, false, true, null);
		assertEquals("name", p.getName());
		assertSame(ref, p.getType());
		assertFalse(p.isNullable());
		assertTrue(p.isCollection());
		assertNull(p.getCollectionIndex());
	}
	
	@Test
	public void constructor_indexed_collection() {
		SidoType ref = mock(SidoType.class);
		DefaultSidoRefProperty p = new DefaultSidoRefProperty("users", ref, false, true, "name");
		assertEquals("users", p.getName());
		assertSame(ref, p.getType());
		assertFalse(p.isNullable());
		assertTrue(p.isCollection());
		assertEquals("name", p.getCollectionIndex());
	}

}
