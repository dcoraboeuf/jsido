package net.sf.sido.schema.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.sido.schema.SidoSimpleType;
import net.sf.sido.schema.support.DefaultSidoContext;

import org.joda.time.DateTime;
import org.junit.Test;

public class DateTimeSimpleTypeTest {

	@Test
	public void registration() {
		DefaultSidoContext context = new DefaultSidoContext();
		SidoSimpleType<Object> type = context.getSimpleType("datetime", true);
		assertEquals("datetime", type.getName());
		assertEquals(DateTime.class, type.getType());
		Object o = type.getDefaultValue();
		assertNotNull(o);
		assertTrue(o instanceof DateTime);
	}

}
