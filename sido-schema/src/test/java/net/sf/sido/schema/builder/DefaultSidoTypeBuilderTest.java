package net.sf.sido.schema.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoSimpleType;
import net.sf.sido.schema.SidoType;

import org.junit.Test;

public class DefaultSidoTypeBuilderTest {
	
	@Test(expected = NullPointerException.class)
	public void constructor_no_schema() {
		new DefaultSidoTypeBuilder(null, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void constructor_no_name() {
		new DefaultSidoTypeBuilder(mock(SidoSchemaBuilder.class), null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructor_blank_name() {
		SidoSchema schema = mock(SidoSchema.class);
		SidoSchemaBuilder schemaBuilder = mock(SidoSchemaBuilder.class);
		when(schemaBuilder.getSchema()).thenReturn(schema);
		new DefaultSidoTypeBuilder(schemaBuilder, " ");
	}
	
	@Test
	public void constructor() {
		SidoSchema schema = mock(SidoSchema.class);
		SidoSchemaBuilder schemaBuilder = mock(SidoSchemaBuilder.class);
		when(schemaBuilder.getSchema()).thenReturn(schema);
		DefaultSidoTypeBuilder builder = new DefaultSidoTypeBuilder(schemaBuilder, "Person");
		SidoType type = builder.getType();
		assertNotNull(type);
		assertEquals("Person", type.getName());
		assertFalse(type.isAbstractType());
		assertNull(type.getParentType());
		assertTrue(type.getProperties().isEmpty());
		assertSame(schema, type.getSchema());
	}
	
	@Test
	public void parent_type() {
		SidoSchema schema = mock(SidoSchema.class);
		SidoSchemaBuilder schemaBuilder = mock(SidoSchemaBuilder.class);
		when(schemaBuilder.getSchema()).thenReturn(schema);
		DefaultSidoTypeBuilder builder = new DefaultSidoTypeBuilder(schemaBuilder, "Person");
		SidoType parentType = mock(SidoType.class);
		SidoTypeBuilder returnedBuilder = builder.setParentType(parentType);
		assertSame(builder, returnedBuilder);
		SidoType type = builder.getType();
		assertSame(parentType, type.getParentType());
	}
	
	@Test
	public void abstract_type() {
		SidoSchema schema = mock(SidoSchema.class);
		SidoSchemaBuilder schemaBuilder = mock(SidoSchemaBuilder.class);
		when(schemaBuilder.getSchema()).thenReturn(schema);
		DefaultSidoTypeBuilder builder = new DefaultSidoTypeBuilder(schemaBuilder, "Person");
		SidoTypeBuilder returnedBuilder = builder.setAbstract();
		assertSame(builder, returnedBuilder);
		SidoType type = builder.getType();
		assertTrue(type.isAbstractType());
	}
	
	@Test
	public void close() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Builder
		SidoSchemaBuilder schemaBuilder = mock(SidoSchemaBuilder.class);
		when(schemaBuilder.getSchema()).thenReturn(schema);
		// Type builder
		DefaultSidoTypeBuilder builder = new DefaultSidoTypeBuilder(schemaBuilder, "Person");
		// Closes the builder
		builder.close();
		verify(schemaBuilder, times(1)).close(builder);
	}
	
	@Test
	public void anonymous_property() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Builder
		SidoSchemaBuilder schemaBuilder = mock(SidoSchemaBuilder.class);
		when(schemaBuilder.getSchema()).thenReturn(schema);
		// Type builder
		DefaultSidoTypeBuilder builder = new DefaultSidoTypeBuilder(schemaBuilder, "Person");
		// Adds the property
		SidoAnonymousProperty p = builder.addAnonymousProperty("data", false, false);
		assertNotNull(p);
		assertEquals("data", p.getName());
		// Checks the property
		assertSame(p, builder.getType().getProperty("data", true));
	}
	
	@Test
	public void simple_property() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Builder
		SidoSchemaBuilder schemaBuilder = mock(SidoSchemaBuilder.class);
		when(schemaBuilder.getSchema()).thenReturn(schema);
		// Type builder
		DefaultSidoTypeBuilder builder = new DefaultSidoTypeBuilder(schemaBuilder, "Person");
		// Adds the property
		@SuppressWarnings("unchecked")
		SidoSimpleType<String> simpleType = mock (SidoSimpleType.class);
		SidoSimpleProperty<String> p = builder.addProperty("data", simpleType, false, false);
		assertNotNull(p);
		assertEquals("data", p.getName());
		assertSame(simpleType, p.getType());
		// Checks the property
		assertSame(p, builder.getType().getProperty("data", true));
	}
	
	@Test
	public void ref_property() {
		// Schema
		SidoSchema schema = mock(SidoSchema.class);
		// Builder
		SidoSchemaBuilder schemaBuilder = mock(SidoSchemaBuilder.class);
		when(schemaBuilder.getSchema()).thenReturn(schema);
		// Type builder
		DefaultSidoTypeBuilder builder = new DefaultSidoTypeBuilder(schemaBuilder, "Person");
		// Adds the property
		SidoType ref = mock (SidoType.class);
		SidoRefProperty p = builder.addProperty("data", ref, false, true, "index");
		assertNotNull(p);
		assertEquals("data", p.getName());
		assertSame(ref, p.getType());
		assertTrue(p.isCollection());
		assertEquals("index", p.getCollectionIndex());
		// Checks the property
		assertSame(p, builder.getType().getProperty("data", true));
	}

}
