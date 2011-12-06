package net.sf.sido.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import net.sf.sido.parser.actions.SidoParsingAction;
import net.sf.sido.schema.SidoPrefix;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.builder.SchemaBuilder;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;

public class SidoParserTest {
	
	@Test
	public void schema_only() {
		SidoParsingAction action = new SidoParsingAction();
		SidoParser parser = Parboiled.createParser(SidoParser.class, action);
		ReportingParseRunner<Object> runner = new ReportingParseRunner<Object>(parser.schema());
		ParsingResult<Object> result = runner.run("schema <sido.test.simple>.");
		assertParsingResult(result);
		SchemaBuilder builder = action.getSchemaBuilder();
		assertNotNull(builder);
		SidoSchema schema = builder.build();
		assertNotNull(schema);
		assertEquals("sido.test.simple", schema.getUri());
		assertNotNull(schema.getPrefixes());
		assertTrue(schema.getPrefixes().isEmpty());
	}
	
	@Test
	public void schema_and_prefixes() {
		SidoParsingAction action = new SidoParsingAction();
		SidoParser parser = Parboiled.createParser(SidoParser.class, action);
		ReportingParseRunner<Object> runner = new TracingParseRunner<Object>(parser.schema());
		ParsingResult<Object> result = runner.run("schema < sido.test.prefixes > . uses api for  <sido.test.api > . uses core for < sido.test.core > .");
		assertParsingResult(result);
		SchemaBuilder builder = action.getSchemaBuilder();
		assertNotNull(builder);
		SidoSchema schema = builder.build();
		assertNotNull(schema);
		assertEquals("sido.test.prefixes", schema.getUri());
		Map<String, SidoPrefix> prefixes = schema.getPrefixes();
		assertNotNull(prefixes);
		assertEquals(2, prefixes.size());
		{
			SidoPrefix sidoPrefix = prefixes.get("api");
			assertNotNull(sidoPrefix);
			assertEquals("api", sidoPrefix.getPrefix());
			assertEquals("sido.test.api", sidoPrefix.getUri());
		}
		{
			SidoPrefix sidoPrefix = prefixes.get("core");
			assertNotNull(sidoPrefix);
			assertEquals("core", sidoPrefix.getPrefix());
			assertEquals("sido.test.core", sidoPrefix.getUri());
		}
	}
	
	@Test
	public void one_type() {
		SidoParsingAction action = new SidoParsingAction();
		SidoParser parser = Parboiled.createParser(SidoParser.class, action);
		ReportingParseRunner<Object> runner = new ReportingParseRunner<Object>(parser.schema());
		ParsingResult<Object> result = runner.run("schema <sido.test>.\na Person.");
		assertParsingResult(result);
		SchemaBuilder builder = action.getSchemaBuilder();
		assertNotNull(builder);
		SidoSchema schema = builder.build();
		assertNotNull(schema);
		assertEquals("sido.test", schema.getUri());
		Map<String, SidoPrefix> prefixes = schema.getPrefixes();
		assertNotNull(prefixes);
		assertTrue(prefixes.isEmpty());
		Map<String, SidoType> types = schema.getTypes();
		assertNotNull(types);
		assertEquals(1, types.size());
		SidoType type = types.get("Person");
		assertNotNull(type);
		assertEquals("Person", type.getName());
	}

	protected void assertParsingResult(ParsingResult<Object> result) {
		assertNotNull(result);
		if (result.hasErrors()) {
			System.out.println(result.parseErrors);
		}
		assertTrue(result.matched);
	}

}
