package net.sf.sido.schema.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

public class SimpleTypeTest {
	
	@Test
	public void builder() {
		SimpleType<Integer> type = SimpleType.get("integer", Integer.class, "0");
		assertNotNull(type);
		assertEquals("integer", type.getName());
		assertEquals(Integer.class, type.getType());
		assertEquals(Integer.valueOf(0), type.getDefaultValue());
		assertEquals("0", type.getDefaultJavaInitialization());
	}
	
	@Test
	public void defaultValue() {
		assertEquals(Boolean.FALSE, SimpleType.getDefaultValue(Boolean.class));
		assertEquals("", SimpleType.getDefaultValue(String.class));
		assertEquals(Byte.valueOf((byte)0), SimpleType.getDefaultValue(Byte.class));
		assertEquals(Short.valueOf((short)0), SimpleType.getDefaultValue(Short.class));
		assertEquals(Integer.valueOf(0), SimpleType.getDefaultValue(Integer.class));
		assertEquals(Long.valueOf(0L), SimpleType.getDefaultValue(Long.class));
		assertEquals(Float.valueOf((float)0), SimpleType.getDefaultValue(Float.class));
		assertEquals(Double.valueOf(0d), SimpleType.getDefaultValue(Double.class));
		assertEquals(BigDecimal.ZERO, SimpleType.getDefaultValue(BigDecimal.class));
		// Any other class?
		assertNull (SimpleType.getDefaultValue(Object.class));
	}

}
