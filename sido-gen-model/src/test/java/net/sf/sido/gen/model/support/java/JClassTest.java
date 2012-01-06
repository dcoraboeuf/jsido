package net.sf.sido.gen.model.support.java;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

/**
 * Unit test for {@link JClass}.
 */
public class JClassTest {
	
	/**
	 * Tests that spacing is correct.
	 * @throws IOException 
	 */
	@Test
	public void spacing() throws IOException {
		// Class structure
		JClass c = new JClass("sido.test", "Test");
		c.addImport(List.class);
		c.addImport(ArrayList.class);
		c.addField("String", "name");
		c.addField("List<String>", "otherNames").setInitialisation("new ArrayList<String>()");
		c.addConstructor();
		c.addConstructor().addParam("String", "value").addContent("this.name = value;");
		c.addMethod("getName", "String").addContent("return name;");
		c.addMethod("setName").addParam("String", "value").addContent("this.name = value;");
		// Output
		StringWriter s = new StringWriter();
		PrintWriter writer = new PrintWriter(s);
		// Generation
		c.write(writer);
		// Output
		List<String> actual = readLines(s.toString());
		// Gets the expected content
		List<String> expected = readReference("/jclass/Spacing.java");
		// Difference between the two sets
		Patch diff = DiffUtils.diff(expected, actual);
		List<Delta> deltas = diff.getDeltas();
		if (!deltas.isEmpty()) {
			// Creates the diff
			String original = StringUtils.join(expected, "\n");
			String revised = StringUtils.join(actual, "\n");
			List<String> unifiedDiff = DiffUtils.generateUnifiedDiff(original,
					revised, expected, diff, 3);
			String diffDisplay = StringUtils.join(unifiedDiff, "\n");
			System.err.println(diffDisplay);
			fail("spacing");
		}
	}

	private List<String> readReference(String referenceResourcePath)
			throws IOException {
		InputStream in = getClass().getResourceAsStream(referenceResourcePath);
		if (in == null) {
			throw new IOException("Cannot find resource at " + referenceResourcePath);
		} else {
			try {
				String content = IOUtils.toString(in, "UTF-8");
				return readLines(content);
			} finally {
				in.close();
			}
		}
	}

	private List<String> readLines(String content) {
		String[] lines = StringUtils.splitPreserveAllTokens(content, "\n");
		return Arrays.asList(lines);
	}

}
