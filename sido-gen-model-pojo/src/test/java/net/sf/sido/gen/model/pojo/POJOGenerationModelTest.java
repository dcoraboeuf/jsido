package net.sf.sido.gen.model.pojo;

import static org.junit.Assert.assertEquals;

import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.model.DefaultSidoAnonymousProperty;
import net.sf.sido.schema.model.DefaultSidoSimpleProperty;
import net.sf.sido.schema.support.SimpleType;

import org.junit.Test;

public class POJOGenerationModelTest {

	private final POJOGenerationModel model = new POJOGenerationModel();

	@Test
	public void getGetMethodName_not_simple() {
		SidoProperty property = new DefaultSidoAnonymousProperty("test", false, false);
		String name = model.getGetMethodName(property);
		assertEquals("getTest", name);
	}

	@Test
	public void getGetMethodName_not_boolean() {
		SidoProperty property = new DefaultSidoSimpleProperty<String>("test",
				SimpleType.get("string", String.class, "\"\""), false, false);
		String name = model.getGetMethodName(property);
		assertEquals("getTest", name);
	}

	@Test
	public void getGetMethodName_boolean_collection() {
		SidoProperty property = new DefaultSidoSimpleProperty<Boolean>("test",
				SimpleType.get("boolean", Boolean.class, "false"), false, true);
		String name = model.getGetMethodName(property);
		assertEquals("getTest", name);
	}

	@Test
	public void getGetMethodName_boolean_nullable() {
		SidoProperty property = new DefaultSidoSimpleProperty<Boolean>("test",
				SimpleType.get("boolean", Boolean.class, "false"), true, false);
		String name = model.getGetMethodName(property);
		assertEquals("getTest", name);
	}

	@Test
	public void getGetMethodName_boolean_not_nullable() {
		SidoProperty property = new DefaultSidoSimpleProperty<Boolean>("test",
				SimpleType.get("boolean", Boolean.class, "false"), false, false);
		String name = model.getGetMethodName(property);
		assertEquals("isTest", name);
	}

}
