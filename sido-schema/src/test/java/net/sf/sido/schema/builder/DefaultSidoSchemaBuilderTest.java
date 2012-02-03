package net.sf.sido.schema.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.support.SidoClosedItemException;

import org.junit.Test;

public class DefaultSidoSchemaBuilderTest {
	
	private static final String UID = "sido.test";

	@Test(expected = NullPointerException.class)
	public void constructor_no_context () {
		new DefaultSidoSchemaBuilder(null, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void constructor_no_uid () {
		SidoContext context = mock (SidoContext.class);
		new DefaultSidoSchemaBuilder(context, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructor_blank_uid () {
		SidoContext context = mock (SidoContext.class);
		new DefaultSidoSchemaBuilder(context, " ");
	}
	
	@Test
	public void constructor_register () {
		SidoContext context = mock (SidoContext.class);
		DefaultSidoSchemaBuilder builder = new DefaultSidoSchemaBuilder(context, UID);
		assertNotNull(builder);
		assertEquals(UID, builder.getUid());
		SidoSchema schema = builder.getSchema();
		assertNotNull(schema);
		assertSame(context, schema.getContext());
		assertEquals(UID, schema.getUid());
		verify(context, times(1)).registerSchema(schema);
	}
	
	@Test(expected = SidoClosedItemException.class)
	public void close() {
		SidoContext context = mock (SidoContext.class);
		DefaultSidoSchemaBuilder builder = new DefaultSidoSchemaBuilder(context, UID);
		builder.close();
		SidoTypeBuilder typeBuilder = builder.newType("Person");
		builder.close(typeBuilder);
	}
	
	@Test
	public void type_creation() {
		SidoContext context = mock (SidoContext.class);
		DefaultSidoSchemaBuilder builder = new DefaultSidoSchemaBuilder(context, UID);
		SidoTypeBuilder type = builder.newType("Person");
		builder.close(type);
		SidoSchema schema = builder.getSchema();
		SidoType person = schema.getType("Person", true);
		assertNotNull(person);
		assertEquals("Person", person.getName());
		assertSame(schema, person.getSchema());
	}

}
