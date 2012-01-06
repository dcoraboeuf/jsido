package net.sf.sido.gen.model.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

public class MapOptionsTest {
	
	@Test
	public void boolean_null () {
		MapOptions options = new MapOptions(Collections.singletonMap("test", "false"));
		assertTrue(options.getBoolean("ttt", true));
	}
	
	@Test
	public void boolean_false () {
		MapOptions options = new MapOptions(Collections.singletonMap("test", "false"));
		assertFalse(options.getBoolean("test", true));
	}
	
	@Test
	public void boolean_true () {
		MapOptions options = new MapOptions(Collections.singletonMap("test", "true"));
		assertTrue(options.getBoolean("test", false));
	}
	
	@Test
	public void int_null () {
		MapOptions options = new MapOptions(Collections.singletonMap("test", "0"));
		assertEquals(1, options.getInt("ttt", 1));
	}
	
	@Test
	public void int_value () {
		MapOptions options = new MapOptions(Collections.singletonMap("test", "12"));
		assertEquals(12, options.getInt("test", 10));
	}
	
	@Test
	public void long_null () {
		MapOptions options = new MapOptions(Collections.singletonMap("test", "0"));
		assertEquals(1L, options.getLong("ttt", 1));
	}
	
	@Test
	public void long_value () {
		MapOptions options = new MapOptions(Collections.singletonMap("test", "12"));
		assertEquals(12L, options.getLong("test", 10L));
	}
	
	@Test
	public void string_null () {
		MapOptions options = new MapOptions(Collections.singletonMap("test", "A"));
		assertEquals("B", options.getString("ttt", "B"));
	}
	
	@Test
	public void string_value () {
		MapOptions options = new MapOptions(Collections.singletonMap("test", "A"));
		assertEquals("A", options.getString("test", "B"));
	}
	
	@Test
	public void class_null () {
		MapOptions options = new MapOptions(Collections.singletonMap("test", "java.lang.String"));
		assertEquals(Long.class, options.getClass("ttt", Long.class));
	}
	
	@Test
	public void class_value () {
		MapOptions options = new MapOptions(Collections.singletonMap("test", "java.lang.String"));
		assertEquals(String.class, options.getClass("test", Long.class));
	}

}
