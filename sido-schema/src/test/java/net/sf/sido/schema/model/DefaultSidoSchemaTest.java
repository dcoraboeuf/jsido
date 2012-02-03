package net.sf.sido.schema.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.support.SidoClosedItemException;
import net.sf.sido.schema.support.SidoTypeNotFoundInSchemaException;

import org.junit.Test;

public class DefaultSidoSchemaTest {

	private static final String UID = "sido.test";

	@Test(expected = NullPointerException.class)
	public void constructor_no_context() {
		new DefaultSidoSchema(null, null);
	}

	@Test(expected = NullPointerException.class)
	public void constructor_no_uid() {
		new DefaultSidoSchema(mock(SidoContext.class), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_blank_uid() {
		new DefaultSidoSchema(mock(SidoContext.class), "  ");
	}

	@Test
	public void constructor() {
		SidoContext context = mock(SidoContext.class);
		DefaultSidoSchema schema = new DefaultSidoSchema(context, UID);
		assertSame(context, schema.getContext());
		assertEquals(UID, schema.getUid());
		assertEquals(UID, schema.toString());
		Collection<SidoType> types = schema.getTypes();
		assertNotNull(types);
		assertTrue(types.isEmpty());
	}

	@Test(expected = SidoClosedItemException.class)
	public void addType_closed() {
		SidoContext context = mock(SidoContext.class);
		DefaultSidoSchema schema = new DefaultSidoSchema(context, UID);
		schema.close();
		schema.addType(mock(SidoType.class));
	}

	@Test
	public void getType_not_required() {
		SidoContext context = mock(SidoContext.class);
		DefaultSidoSchema schema = new DefaultSidoSchema(context, UID);
		SidoType type = schema.getType("Person", false);
		assertNull(type);
	}

	@Test(expected = SidoTypeNotFoundInSchemaException.class)
	public void getType_required() {
		SidoContext context = mock(SidoContext.class);
		DefaultSidoSchema schema = new DefaultSidoSchema(context, UID);
		schema.getType("Person", true);
	}

	@Test
	public void addType() {
		SidoContext context = mock(SidoContext.class);
		DefaultSidoSchema schema = new DefaultSidoSchema(context, UID);
		SidoType type = mock(SidoType.class);
		when(type.getName()).thenReturn("Person");
		schema.addType(type);
		assertSame (type, schema.getType("Person", true));
	}

}
