package net.sf.sido.gen.model.support.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
	
	@Test
	public void referenceName_no_parameter() {
		JClass c = new JClass("test", "MyClass");
		assertEquals("MyClass", c.getReferenceName());
	}
	
	@Test
	public void referenceName_one_parameter() {
		JClass c = new JClass("test", "MyClass").addParameter(new JClass("test.api", "Model"));
		assertEquals("MyClass<Model>", c.getReferenceName());
	}
	
	@Test
	public void referenceName_two_parameters() {
		JClass c = new JClass("test", "MyClass").addParameter(new JClass("test.api", "Model")).addParameter(String.class);
		assertEquals("MyClass<Model,String>", c.getReferenceName());
	}
	
	/**
	 * Parameters in declaration
	 */
	@Test
	public void parameters_in_declaration() throws IOException {
		JClass c = new JClass("test", "MyClass").addParameter(new JClass("test.api", "Model"));
		assertJClass(c, "/jclass/ParametersInDeclaration.java", "parameters_in_declaration");
	}
	
	/**
	 * Parameters in fields
	 */
	@Test
	public void parameters_in_fields() throws IOException {
		JClass c = new JClass("test", "MyClass");
		c.addField(new JClass("test.api", "Model").addParameter(String.class), "model");
		assertJClass(c, "/jclass/ParametersInFields.java", "parameters_in_fields");
	}
	
	@Test
	public void parameters_as_return () throws IOException {
		JClass c = new JClass("test", "MyClass");
		c.addMethod("getValue", new JClass("test.api", "Model").addParameter(String.class)).addContent("return null;");
		assertJClass(c, "/jclass/ParametersAsReturn.java", "parameters_as_return");
	}
	
	@Test
	public void parameters_as_param () throws IOException {
		JClass c = new JClass("test", "MyClass");
		c.addMethod("setValue").addParam(new JClass("test.api", "Model").addParameter(String.class), "value").addContent("// Nothing");
		assertJClass(c, "/jclass/ParametersAsParam.java", "parameters_as_param");
	}

	public void assertJClass(JClass c, String referenceResourcePath,
			String testId) throws IOException {
		// Output
		StringWriter s = new StringWriter();
		PrintWriter writer = new PrintWriter(s);
		// Generation
		c.write(writer);
		// Output
		List<String> actual = readLines(s.toString());
		// Gets the expected content
		List<String> expected = readReference(referenceResourcePath);
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
			fail(testId);
		}
	}

	/**
	 * Primitive type
	 */
	@Test
	public void primitive() {
		JClass c = new JClass(Integer.TYPE);
		assertNull(c.getPackageName());
		assertEquals("int", c.getName());
	}

	/**
	 * Primitive type cannot be written
	 * 
	 * @throws IOException
	 */
	@Test(expected = IllegalStateException.class)
	public void primitive_cannot_be_written() throws IOException {
		JClass c = new JClass(Integer.TYPE);
		c.write(new PrintWriter(new StringWriter()));
	}

	/**
	 * Primitive type as an import
	 */
	@Test
	public void primitive_as_import() throws IOException {
		JClass intClass = new JClass(Integer.TYPE);
		JClass hostClass = new JClass("test", "Test");
		hostClass.addImport(intClass);
		Set<String> imports = hostClass.getImportNames();
		assertNotNull(imports);
		assertTrue(imports.isEmpty());
	}

	/**
	 * Tests that spacing is correct.
	 * 
	 * @throws IOException
	 */
	@Test
	public void spacing() throws IOException {
		// Class structure
		JClass c = new JClass("sido.test", "Test");
		c.addImport(List.class);
		c.addImport(ArrayList.class);
		c.addField("String", "name");
		c.addField("List<String>", "otherNames").setInitialisation(
				"new ArrayList<String>()");
		c.addConstructor();
		c.addConstructor().addParam("String", "value")
				.addContent("this.name = value;");
		c.addMethod("getName", "String").addContent("return name;");
		c.addMethod("setName").addParam("String", "value")
				.addContent("this.name = value;");
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

	/**
	 * Tests that spacing is correct.
	 * 
	 * @throws IOException
	 */
	@Test
	public void spacing_no_constructor() throws IOException {
		// Class structure
		JClass c = new JClass("sido.test", "Test");
		c.addImport(List.class);
		c.addImport(ArrayList.class);
		c.addField("String", "name");
		c.addField("List<String>", "otherNames").setInitialisation(
				"new ArrayList<String>()");
		c.addMethod("getName", "String").addContent("return name;");
		c.addMethod("setName").addParam("String", "value")
				.addContent("this.name = value;");
		// Output
		StringWriter s = new StringWriter();
		PrintWriter writer = new PrintWriter(s);
		// Generation
		c.write(writer);
		// Output
		List<String> actual = readLines(s.toString());
		// Gets the expected content
		List<String> expected = readReference("/jclass/SpacingNoConstructor.java");
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
			fail("spacing-no-constructor");
		}
	}

	private List<String> readReference(String referenceResourcePath)
			throws IOException {
		InputStream in = getClass().getResourceAsStream(referenceResourcePath);
		if (in == null) {
			throw new IOException("Cannot find resource at "
					+ referenceResourcePath);
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
