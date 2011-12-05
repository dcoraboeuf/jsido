package net.sf.sido.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;

public class SidoParserTest {
	
	@Test
	public void schema_only() {
		SidoParser parser = Parboiled.createParser(SidoParser.class);
		ReportingParseRunner<Object> runner = new TracingParseRunner<Object>(parser.schema());
		ParsingResult<Object> result = runner.run("schema <sido.test.simple>.");
		assertParsingResult(result);
	}
	
	@Test
	public void schema_and_prefixes() {
		SidoParser parser = Parboiled.createParser(SidoParser.class);
		ReportingParseRunner<Object> runner = new TracingParseRunner<Object>(parser.schema());
		ParsingResult<Object> result = runner.run("schema < sido.test.prefixes > . uses api for  <sido.test.api > . uses core for < sido.test.core > .");
		assertParsingResult(result);
	}

	protected void assertParsingResult(ParsingResult<Object> result) {
		assertNotNull(result);
		if (result.hasErrors()) {
			System.out.println(result.parseErrors);
		}
		assertTrue(result.matched);
	}

}
