package net.sf.sido.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import net.sf.sido.schema.SidoSchema;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class ParserTest {

	@Test
	public void name_only() {
		SidoSchema schema = parse("name-only");
		assertNotNull("Returned schema is null", schema);
		assertEquals("sido.test", schema.getUid());
		assertTrue(schema.getTypes().isEmpty());
	}

	private SidoSchema parse(String fileName) {
		String path = String.format("/sidol/schema-%s.sidol", fileName);
		InputStream in = getClass().getResourceAsStream(path);
		if (in == null) {
			throw new IllegalStateException("Cannot find resource at " + path);
		} else {
			String sidol;
			try {
				sidol = IOUtils.toString(in);
			} catch (IOException e) {
				throw new IllegalStateException(String.format(
						"Cannot read resource at %s", path), e);
			}
			SidoParser parser = SidoParserFactory.createParser();
			SidoSchema schema = parser.parse(sidol);
			return schema;
		}
	}

}
