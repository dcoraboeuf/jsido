package net.sf.sido.schema.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import net.sf.sido.schema.SidoSchema;

import org.junit.Before;
import org.junit.Test;

public class DefaultSidoContextTest {

	private DefaultSidoContext context;

	@Before
	public void before() {
		context = new DefaultSidoContext();
	}

	@Test
	public void schema_not_found_null() {
		Collection<SidoSchema> schemas = context.getSchemas();
		assertNotNull(schemas);
		assertTrue(schemas.isEmpty());
		SidoSchema schema = context.getSchema("test", false);
		assertNull(schema);
	}

	@Test(expected = SidoSchemaNotFoundException.class)
	public void schema_not_found_required() {
		context.getSchema("test", true);
	}

	@Test
	public void register() {
		SidoSchema schema = mock(SidoSchema.class);
		when(schema.getUid()).thenReturn("test");
		context.registerSchema(schema);
		schema = context.getSchema("test", true);
		assertEquals("test", schema.getUid());
	}

	@Test(expected = SidoSchemaUIDDuplicationException.class)
	public void register_duplicate() {
		SidoSchema schema = mock(SidoSchema.class);
		when(schema.getUid()).thenReturn("test");
		context.registerSchema(schema);
		context.registerSchema(schema);
	}

}
