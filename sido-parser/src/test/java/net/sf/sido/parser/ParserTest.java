package net.sf.sido.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import net.sf.sido.schema.SidoSchema;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class ParserTest {

	@Test
	public void name_only() {
		SidoSchema schema = parseOne("name-only");
		assertNotNull("Returned schema is null", schema);
		assertEquals("sido.test", schema.getUid());
		assertTrue(schema.getTypes().isEmpty());
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
		Collection<String> inputs = Collections2.transform(
				Arrays.asList(fileNames), new Function<String, String>() {
					@Override
					public String apply(String fileName) {
						String path = String.format("/sidol/schema-%s.sidol",
								fileName);
						InputStream in = getClass().getResourceAsStream(path);
						if (in == null) {
							throw new IllegalStateException(
									"Cannot find resource at " + path);
						} else {
							String sidol;
							try {
								sidol = IOUtils.toString(in);
								return sidol;
							} catch (IOException e) {
								throw new IllegalStateException(String.format(
										"Cannot read resource at %s", path), e);
							}
						}
					}
				});
		SidoParser parser = SidoParserFactory.createParser();
		return parser.parse(inputs);
	}

}
