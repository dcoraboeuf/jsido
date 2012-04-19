package net.sf.sido.parser.discovery.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.sf.sido.parser.discovery.SidoSchemaDiscovery;

import org.junit.Test;

public class DefaultSidoDiscoveryTest {
	
	private final DefaultSidoDiscovery discovery = new DefaultSidoDiscovery();
	
	@Test(expected = NullPointerException.class)
	public void parse_null() {
		discovery.parseDiscovery(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parse_blank() {
		discovery.parseDiscovery("   ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parse_too_much() {
		discovery.parseDiscovery("uid tokens rubish");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parse_too_much_tokens() {
		discovery.parseDiscovery("uid model=test=wrong");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parse_unknown_key() {
		discovery.parseDiscovery("uid model=pojo,xxx=wrong");
	}
	
	@Test
	public void parse_uid_only() {
		SidoSchemaDiscovery o = discovery.parseDiscovery("net.sf.sido.model");
		assertNotNull(o);
		assertEquals("net.sf.sido.model", o.getUid());
		assertEquals("pojo", o.getModel());
	}
	
	@Test
	public void parse_uid_and_model() {
		SidoSchemaDiscovery o = discovery.parseDiscovery("net.sf.sido.model model=xpojo");
		assertNotNull(o);
		assertEquals("net.sf.sido.model", o.getUid());
		assertEquals("xpojo", o.getModel());
	}

}
