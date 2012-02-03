package net.sf.sido.schema.builder;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import net.sf.sido.schema.SidoContext;

import org.junit.Test;

public class SidoSchemaBuilderFactoryTest {
	
	@Test
	public void factory() {
		SidoContext context = mock(SidoContext.class);
		SidoSchemaBuilderFactory factory = SidoSchemaBuilderFactory.newInstance(context);
		assertNotNull(factory);
	}
	
	@Test
	public void schema() {
		SidoContext context = mock(SidoContext.class);
		SidoSchemaBuilderFactory factory = SidoSchemaBuilderFactory.newInstance(context);
		SidoSchemaBuilder schemaBuilder = factory.create("sido.test");
		assertNotNull(schemaBuilder);
	}

}
