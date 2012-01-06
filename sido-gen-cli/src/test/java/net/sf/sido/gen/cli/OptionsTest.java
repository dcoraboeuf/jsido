package net.sf.sido.gen.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class OptionsTest {

	private Options options;
	private CmdLineParser parser;

	@Before
	public void config() {
		options = new Options();
		parser = new CmdLineParser(options);
	}

	@Test
	public void noArgument() throws CmdLineException {
		parser.parseArgument();
		assertEquals("pojo", options.model);
		assertEquals(new File("."), options.sourceDirectory);
		assertEquals(new File("."), options.outputDirectory);
		assertTrue(options.options.isEmpty());
	}

	@Test
	public void noOptions() throws CmdLineException {
		parser.parseArgument("--model", "javafx", "--source", "sidol", "--output", "target");
		assertEquals("javafx", options.model);
		assertEquals(new File("sidol"), options.sourceDirectory);
		assertEquals(new File("target"), options.outputDirectory);
		assertTrue(options.options.isEmpty());
	}

	@Test
	public void noOptionsShortMode() throws CmdLineException {
		parser.parseArgument("-m", "javafx", "-s", "sidol", "-o", "target");
		assertEquals("javafx", options.model);
		assertEquals(new File("sidol"), options.sourceDirectory);
		assertEquals(new File("target"), options.outputDirectory);
		assertTrue(options.options.isEmpty());
	}

	@Test
	public void oneOption() throws CmdLineException {
		parser.parseArgument("--option", "name=value");
		assertEquals("pojo", options.model);
		assertEquals(new File("."), options.sourceDirectory);
		assertEquals(new File("."), options.outputDirectory);
		assertEquals(1, options.options.size());
		assertEquals("value", options.options.get("name"));
	}

	@Test
	public void twoOptions() throws CmdLineException {
		parser.parseArgument("--option", "name=value", "--option", "test=value2");
		assertEquals("pojo", options.model);
		assertEquals(new File("."), options.sourceDirectory);
		assertEquals(new File("."), options.outputDirectory);
		assertEquals(2, options.options.size());
		assertEquals("value", options.options.get("name"));
		assertEquals("value2", options.options.get("test"));
	}

}
