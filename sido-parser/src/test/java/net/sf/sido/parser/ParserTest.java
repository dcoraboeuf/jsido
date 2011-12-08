package net.sf.sido.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import net.sf.jstring.Strings;
import net.sf.sido.parser.support.SidoParseException;
import net.sf.sido.schema.Sido;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.support.DefaultSidoContext;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class ParserTest {

	private static Strings strings;

	@BeforeClass
	public static void load() {
		strings = new Strings("net.sf.sido.parser.Strings");
	}

	private SidoContext context;

	@Before
	public void before() {
		context = new DefaultSidoContext();
		Sido.setContext(context);
	}

	// TODO Already loaded modules
	// TODO Circularity

	@Test
	public void name_only() {
		SidoSchema schema = parseOne("name-only");
		assertNotNull("Returned schema is null", schema);
		assertEquals("sido.test", schema.getUid());
		assertTrue(schema.getTypes().isEmpty());
	}

	@Test
	public void complex() {
		try {
			SidoSchema schema = parseOne("complex");
			assertNotNull("Returned schema is null", schema);
			assertEquals("sido.test", schema.getUid());
			assertEquals(5, schema.getTypes().size());
		} catch (SidoParseException ex) {
			fail(ex.getLocalizedMessage(strings, Locale.ENGLISH));
		}
	}

	@Test
	public void parsing_error_0() {
		try {
			parseOne("parsing-error-0");
			fail("Expected parsing error");
		} catch (SidoParseException ex) {
			assertEquals(
					"Error while parsing \"parsing-error-0\":  - Input \"<\" (position 32 to 33) is not valid for:\n"
							+ " - schema/prefix_list/Sequence/prefix/whitespaces/whitespace\n"
							+ " - schema/prefix_list/Sequence/prefix/\"for\"/'f'\n" + ".\n",
						ex.getLocalizedMessage(strings, Locale.ENGLISH));
		}
	}

	@Test
	public void modules() {
		try {
			Collection<SidoSchema> schemas = parse("module-2", "module-1", "module-0");
			// Simple check
			assertNotNull("Returned schemas are null", schemas);
			assertEquals(3, schemas.size());
			// Context
			assertModules();
		} catch (SidoParseException ex) {
			fail(ex.getLocalizedMessage(strings, Locale.ENGLISH));
		}
	}

	private void assertModules() {
		// Context
		Collection<SidoSchema> schemas = context.getSchemas();
		assertNotNull(schemas);
		assertEquals(3, schemas.size());
		// Schema names
		SidoSchema sMain = context.getSchema("sido.test.main", true);
		SidoSchema sAddress = context.getSchema("sido.test.address", true);
		SidoSchema sCompany = context.getSchema("sido.test.company", true);
		assertNotNull(sMain);
		assertNotNull(sAddress);
		assertNotNull(sCompany);
		// TODO Schema checks
	}

	private SidoSchema parseOne(String fileName) {
		Collection<SidoSchema> schemas = parse(fileName);
		if (schemas != null) {
			if (schemas.isEmpty()) {
				return null;
			} else if (schemas.size() == 1) {
				return schemas.iterator().next();
			} else {
				throw new IllegalStateException(String.format(
						"The parsing returned more than 1 schema: %d",
							schemas.size()));
			}
		} else {
			return null;
		}
	}

	private Collection<SidoSchema> parse(String... fileNames) {
		Collection<NamedInput> inputs = Collections2.transform(Arrays.asList(fileNames), new Function<String, NamedInput>() {
			@Override
			public NamedInput apply(String fileName) {
				String path = String.format("/sidol/schema-%s.sidol", fileName);
				InputStream in = getClass().getResourceAsStream(path);
				if (in == null) {
					throw new IllegalStateException("Cannot find resource at " + path);
				} else {
					String sidol;
					try {
						sidol = IOUtils.toString(in);
						return new NamedInput(fileName, sidol);
					} catch (IOException e) {
						throw new IllegalStateException(String.format("Cannot read resource at %s", path), e);
					}
				}
			}
		});
		SidoParser parser = SidoParserFactory.createParser();
		return parser.parse(inputs);
	}

}
