package net.sf.sido.schema.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.sido.schema.SidoSchema;

import org.junit.Test;

public class SchemaBuilderTest {
	
	@Test
	public void schema_only() {
		SidoSchema schema = SchemaBuilder.create("sido.test.simple").build();
		assertNotNull(schema);
		assertEquals("sido.test.simple", schema.getUri());
		assertNotNull(schema.getPrefixes());
		assertTrue(schema.getPrefixes().isEmpty());
	}

}
