package net.sf.sido.schema.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import net.sf.sido.schema.SidoSimpleType;

import org.junit.Test;

public class DefaultSidoSimplePropertyTest {
	
	@Test(expected = NullPointerException.class)
	public void constructor_no_name() {
		new DefaultSidoSimpleProperty<String>(null, null, false, false);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructor_blank_name() {
		new DefaultSidoSimpleProperty<String>(" ", null, false, false);
	}
	
	@Test(expected = NullPointerException.class)
	public void constructor_no_type() {
		new DefaultSidoSimpleProperty<String>("name", null, false, false);
	}
	
	@Test
	public void constructor() {
		@SuppressWarnings("unchecked")
		SidoSimpleType<String> ref = (SidoSimpleType<String>) mock (SidoSimpleType.class);
		DefaultSidoSimpleProperty<String> p = new DefaultSidoSimpleProperty<String>("name", ref, false, false);
		assertEquals("name", p.getName());
		assertSame(ref, p.getType());
		assertFalse(p.isNullable());
		assertFalse(p.isCollection());
		assertNull(p.getCollectionIndex());
	}
	
	@Test
	public void constructor_nullable() {
		@SuppressWarnings("unchecked")
		SidoSimpleType<String> ref = (SidoSimpleType<String>) mock (SidoSimpleType.class);
		DefaultSidoSimpleProperty<String> p = new DefaultSidoSimpleProperty<String>("name", ref, true, false);
		assertEquals("name", p.getName());
		assertSame(ref, p.getType());
		assertTrue(p.isNullable());
		assertFalse(p.isCollection());
		assertNull(p.getCollectionIndex());
	}
	
	@Test
	public void constructor_collection() {
		@SuppressWarnings("unchecked")
		SidoSimpleType<String> ref = (SidoSimpleType<String>) mock (SidoSimpleType.class);
		DefaultSidoSimpleProperty<String> p = new DefaultSidoSimpleProperty<String>("name", ref, false, true);
		assertEquals("name", p.getName());
		assertSame(ref, p.getType());
		assertFalse(p.isNullable());
		assertTrue(p.isCollection());
		assertNull(p.getCollectionIndex());
	}

}
