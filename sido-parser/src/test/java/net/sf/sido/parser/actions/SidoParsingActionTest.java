package net.sf.sido.parser.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class SidoParsingActionTest {

	@Test
	public void parseUri_null() {
		SidoParsingAction action = new SidoParsingAction();
		assertNull(action.parseUri(null));
	}

	@Test
	public void parseUri_empty() {
		SidoParsingAction action = new SidoParsingAction();
		assertEquals("", action.parseUri(""));
	}

	@Test
	public void parseUri_blank() {
		SidoParsingAction action = new SidoParsingAction();
		assertEquals("", action.parseUri("  "));
	}

	@Test
	public void parseUri_sep_only() {
		SidoParsingAction action = new SidoParsingAction();
		assertEquals("", action.parseUri("<>"));
	}

	@Test
	public void parseUri_sep_and_blank() {
		SidoParsingAction action = new SidoParsingAction();
		assertEquals("", action.parseUri("<   >"));
	}

	@Test
	public void parseUri_id() {
		SidoParsingAction action = new SidoParsingAction();
		assertEquals("id", action.parseUri("id"));
	}

	@Test
	public void parseUri_id_blanks() {
		SidoParsingAction action = new SidoParsingAction();
		assertEquals("id", action.parseUri("  id "));
	}

	@Test
	public void parseUri_id_sep() {
		SidoParsingAction action = new SidoParsingAction();
		assertEquals("id", action.parseUri("<id>"));
	}

	@Test
	public void parseUri_id_sep_blanks() {
		SidoParsingAction action = new SidoParsingAction();
		assertEquals("id", action.parseUri(" < id >"));
	}

	@Test
	public void parseUri_uid() {
		SidoParsingAction action = new SidoParsingAction();
		assertEquals("id.uid", action.parseUri("id.uid"));
	}

	@Test
	public void parseUri_uid_blanks() {
		SidoParsingAction action = new SidoParsingAction();
		assertEquals("id.uid", action.parseUri("  id.uid "));
	}

	@Test
	public void parseUri_uid_sep() {
		SidoParsingAction action = new SidoParsingAction();
		assertEquals("id.uid", action.parseUri("<id.uid>"));
	}

	@Test
	public void parseUri_uid_sep_blanks() {
		SidoParsingAction action = new SidoParsingAction();
		assertEquals("id.uid", action.parseUri(" < id.uid >"));
	}

}
