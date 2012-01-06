package net.sf.sido.gen;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.sido.gen.model.GenerationListener;
import net.sf.sido.gen.model.Options;
import net.sf.sido.gen.model.pojo.POJOGenerationModel;
import net.sf.sido.gen.model.support.MapOptions;
import net.sf.sido.gen.model.support.RecordingGenerationOutput;
import net.sf.sido.gen.support.GenerationConfigurationBuilder;
import net.sf.sido.gen.support.ResourceGenerationInput;
import net.sf.sido.schema.Sido;
import net.sf.sido.schema.support.DefaultSidoContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

public class GenerationToolTest {
	
	@Before
	public void init() {
		// Uses a specific context for each test
		Sido.setDefault(new DefaultSidoContext());
	}

	@Test
	public void pojo_simple() throws IOException {
		GenerationTool tool = new GenerationTool();

		// Mock listener
		GenerationListener listener = mock(GenerationListener.class);

		// Sources
		GenerationInput source = new ResourceGenerationInput(
				"/test/sources/simple.sidol");
		Collection<GenerationInput> sources = Collections.singleton(source);

		// Output
		RecordingGenerationOutput output = new RecordingGenerationOutput();
		
		// Options
		Map<String, String> map = new HashMap<String, String>();
		Options options = new MapOptions(map);

		// Configuration
		GenerationConfiguration configuration = GenerationConfigurationBuilder
				.create().modelId("pojo").sources(sources).output(output)
				.options(options)
				.build();

		// Call
		tool.generate(configuration, listener);

		// Checks the output
		Map<String, String> files = output.getFiles();
		checkOutput(files, "sido.test.Person.java",
				"/test/output/pojo/simple/Person.java");
	}

	@Test
	public void pojo_collection_non_final() throws IOException {
		GenerationTool tool = new GenerationTool();

		// Mock listener
		GenerationListener listener = mock(GenerationListener.class);

		// Sources
		GenerationInput source = new ResourceGenerationInput(
				"/test/sources/collection.sidol");
		Collection<GenerationInput> sources = Collections.singleton(source);

		// Output
		RecordingGenerationOutput output = new RecordingGenerationOutput();
		
		// Options
		Map<String, String> map = new HashMap<String, String>();
		Options options = new MapOptions(map);

		// Configuration
		GenerationConfiguration configuration = GenerationConfigurationBuilder
				.create().modelId("pojo").sources(sources).output(output)
				.options(options)
				.build();

		// Call
		tool.generate(configuration, listener);

		// Checks the output
		Map<String, String> files = output.getFiles();
		checkOutput(files, "sido.test.Library.java",
				"/test/output/pojo/collection_non_final/Library.java");
	}

	@Test
	public void pojo_collection_other_implementation() throws IOException {
		GenerationTool tool = new GenerationTool();

		// Mock listener
		GenerationListener listener = mock(GenerationListener.class);

		// Sources
		GenerationInput source = new ResourceGenerationInput(
				"/test/sources/collection.sidol");
		Collection<GenerationInput> sources = Collections.singleton(source);

		// Output
		RecordingGenerationOutput output = new RecordingGenerationOutput();
		
		// Options
		Map<String, String> map = new HashMap<String, String>();
		map.put(POJOGenerationModel.COLLECTION_INTERFACE, Set.class.getName());
		map.put(POJOGenerationModel.COLLECTION_IMPLEMENTATION, HashSet.class.getName());
		Options options = new MapOptions(map);

		// Configuration
		GenerationConfiguration configuration = GenerationConfigurationBuilder
				.create().modelId("pojo").sources(sources).output(output)
				.options(options)
				.build();

		// Call
		tool.generate(configuration, listener);

		// Checks the output
		Map<String, String> files = output.getFiles();
		checkOutput(files, "sido.test.Library.java",
				"/test/output/pojo/collection_other_implementation/Library.java");
	}

	@Test
	public void pojo_collection_final() throws IOException {
		GenerationTool tool = new GenerationTool();

		// Mock listener
		GenerationListener listener = mock(GenerationListener.class);

		// Sources
		GenerationInput source = new ResourceGenerationInput(
				"/test/sources/collection.sidol");
		Collection<GenerationInput> sources = Collections.singleton(source);

		// Output
		RecordingGenerationOutput output = new RecordingGenerationOutput();
		
		// Options
		Map<String, String> map = new HashMap<String, String>();
		map.put(POJOGenerationModel.NON_NULLABLE_COLLECTION_FINAL, "true");
		Options options = new MapOptions(map);

		// Configuration
		GenerationConfiguration configuration = GenerationConfigurationBuilder
				.create().modelId("pojo").sources(sources).output(output)
				.options(options)
				.build();

		// Call
		tool.generate(configuration, listener);

		// Checks the output
		Map<String, String> files = output.getFiles();
		checkOutput(files, "sido.test.Library.java",
				"/test/output/pojo/collection_final/Library.java");
	}

	private void checkOutput(Map<String, String> files, String filePath,
			String referenceResourcePath) throws IOException {
		// Reads the content
		List<String> content = readContent(files, filePath);
		// Reads the reference
		List<String> reference = readReference(referenceResourcePath);
		// Difference between the two sets
		Patch diff = DiffUtils.diff(reference, content);
		List<Delta> deltas = diff.getDeltas();
		if (!deltas.isEmpty()) {
			// Creates the diff
			String original = StringUtils.join(reference, "\n");
			String revised = StringUtils.join(content, "\n");
			List<String> unifiedDiff = DiffUtils.generateUnifiedDiff(original,
					revised, reference, diff, 3);
			for (String line : unifiedDiff) {
				System.err.println(line);
			}
			fail(filePath + " does not conform to the expected result.");
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

	private List<String> readContent(Map<String, String> files, String filePath) {
		String file = files.get(filePath);
		assertNotNull("File " + filePath + " was not generated", file);
		return readLines(file);
	}

	private List<String> readLines(String content) {
		String[] lines = StringUtils.splitPreserveAllTokens(content, "\n");
		return Arrays.asList(lines);
	}

}
