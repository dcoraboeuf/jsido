package net.sf.sido.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Locale;

import net.sf.sido.parser.discovery.support.DefaultSidoDiscovery;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.support.DefaultSidoContext;
import net.sf.sido.schema.support.SidoException;

import org.junit.Test;

public class ExternParserTest extends AbstractParserTest {
	
	private static final String CLIENT_URI = "sido.test.extern.client";

	@Override
	protected SidoContext createContext() {
		// Context
		SidoContext context = new DefaultSidoContext();
		// Discovery
		new DefaultSidoDiscovery().discover(context);
		// OK
		return context;
	}
	
	private static final String API_URI = "sido.test.extern.api";

	@Test
	public void external() {
		// Checks the extern schema
		SidoSchema api = context.getSchema(API_URI, true);
		assertEquals(API_URI, api.getUid());
		SidoType item = assertType(API_URI, "Item", true, null);
		assertProperty(item, "uid", Long.class, false, false, null);
	}
	
	@Test
	public void dependent() {
		try {
			SidoSchema schema = parseOne("extern-client");
			assertNotNull("Returned schema is null", schema);
			assertEquals(CLIENT_URI, schema.getUid());
			assertEquals(1, schema.getTypes().size());
			SidoType item = assertType(API_URI, "Item", true, null);
			SidoType person = assertType(CLIENT_URI, "Person", false, item);
			assertProperty(person, "name", String.class, false, false, null);
		} catch (SidoException ex) {
			ex.printStackTrace();
			fail(ex.getLocalizedMessage(strings, Locale.ENGLISH));
		}
	}

}
