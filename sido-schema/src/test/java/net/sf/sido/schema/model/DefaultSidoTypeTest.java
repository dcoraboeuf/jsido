package net.sf.sido.schema.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.support.SidoClosedItemException;
import net.sf.sido.schema.support.SidoCircularInheritanceException;
import net.sf.sido.schema.support.SidoPropertyNotFoundException;

import org.junit.Test;

public class DefaultSidoTypeTest {

	@Test(expected = NullPointerException.class)
	public void constructor_no_schema() {
		new DefaultSidoType(null, null);
	}

	@Test(expected = NullPointerException.class)
	public void constructor_no_name() {
		new DefaultSidoType(mock(SidoSchema.class), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_blank_name() {
		new DefaultSidoType(mock(SidoSchema.class), "  ");
	}

	@Test
	public void constructor() {
		SidoSchema schema = mock(SidoSchema.class);
		when(schema.getUid()).thenReturn("sido.test");
		DefaultSidoType type = new DefaultSidoType(schema, "Person");
		assertSame(schema, type.getSchema());
		assertEquals("Person", type.getName());
		assertNull(type.getParentType());
		assertEquals("sido.test::Person", type.getQualifiedName());
		assertFalse(type.isAbstractType());
		Collection<SidoProperty> properties = type.getProperties();
		assertNotNull(properties);
		assertTrue(properties.isEmpty());
	}

	@Test(expected = SidoClosedItemException.class)
	public void parent_closed() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Parent
		SidoType parent = mock(SidoType.class);
		// Type
		DefaultSidoType type = new DefaultSidoType(schema, "Person");
		// Closes and set
		type.close();
		type.setParentType(parent);
	}

	@Test
	public void parent() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Parent
		SidoType parent = mock(SidoType.class);
		// Type
		DefaultSidoType type = new DefaultSidoType(schema, "Person");
		// No parent initially
		assertNull(type.getParentType());
		// Sets the parent
		type.setParentType(parent);
		// Check
		assertSame(parent, type.getParentType());
	}

	@Test(expected = IllegalStateException.class)
	public void parent_already_set() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Parent
		SidoType parent = mock(SidoType.class);
		SidoType parent2 = mock(SidoType.class);
		// Type
		DefaultSidoType type = new DefaultSidoType(schema, "Person");
		// Initial parent
		type.setParentType(parent);
		assertSame(parent, type.getParentType());
		// Second parent
		type.setParentType(parent2);
	}

	@Test(expected = SidoCircularInheritanceException.class)
	public void parent_circular() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Type
		DefaultSidoType type = new DefaultSidoType(schema, "Person");
		// Parent
		SidoType parent = mock(SidoType.class);
		when(parent.getParentType()).thenReturn(type);
		// Tries to set the parent whose parent is the type itself
		type.setParentType(parent);
	}

	@Test(expected = SidoClosedItemException.class)
	public void abstract_closed() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Type
		DefaultSidoType type = new DefaultSidoType(schema, "Person");
		// Closes and set
		type.close();
		type.setAbstractType(true);
	}

	@Test
	public void abstract_true() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Type
		DefaultSidoType type = new DefaultSidoType(schema, "Person");
		// Sets
		type.setAbstractType(true);
		// Checks
		assertTrue(type.isAbstractType());
	}

	@Test
	public void abstract_false() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Type
		DefaultSidoType type = new DefaultSidoType(schema, "Person");
		// Sets
		type.setAbstractType(false);
		// Checks
		assertFalse(type.isAbstractType());
	}

	@Test(expected = SidoClosedItemException.class)
	public void addProperty_closed() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Type
		DefaultSidoType type = new DefaultSidoType(schema, "Person");
		// Closes and add
		type.close();
		type.addProperty(mock(SidoProperty.class));
	}

	@Test
	public void addProperty() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Type
		DefaultSidoType type = new DefaultSidoType(schema, "Person");
		// Property
		SidoProperty property = mock(SidoProperty.class);
		when(property.getName()).thenReturn("name");
		// Add
		type.addProperty(property);
		// Check
		assertSame(property, type.getProperty("name", true));
	}

	@Test
	public void getProperty_not_required() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Type
		DefaultSidoType type = new DefaultSidoType(schema, "Person");
		// Property
		SidoProperty property = type.getProperty("name", false);
		assertNull(property);
	}

	@Test(expected = SidoPropertyNotFoundException.class)
	public void getProperty_required() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Type
		DefaultSidoType type = new DefaultSidoType(schema, "Person");
		// Property
		type.getProperty("name", true);
	}

}
