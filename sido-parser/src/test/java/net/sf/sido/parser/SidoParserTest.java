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
	public void simple() {
		SidoParser parser = Parboiled.createParser(SidoParser.class);
		ReportingParseRunner<Object> runner = new TracingParseRunner<Object>(parser.schema());
		ParsingResult<Object> result = runner.run("schema <sido.test.simple>.");
		assertNotNull(result);
		if (result.hasErrors()) {
			System.out.println(result.parseErrors);
		}
		assertTrue(result.matched);
	}

}
