package net.sf.sido.gen;

import static org.mockito.Mockito.mock;

import java.io.IOException;

import net.sf.sido.gen.model.GenerationListener;
import net.sf.sido.gen.support.GenerationConfigurationBuilder;

import org.junit.Ignore;
import org.junit.Test;

public class GenerationToolTest {
	
	@Test
	@Ignore
	public void test() throws IOException {
		GenerationTool tool = new GenerationTool();
		
		// Mock listener
		GenerationListener listener = mock(GenerationListener.class);
		
		// Configuration
		GenerationConfiguration configuration = GenerationConfigurationBuilder.create()
				.modelId("pojo")
				// FIXME Abstract the sources
				//.sources(sources)
				// FIXME Abstract the output
				//.output(output)
				.build();
		
		// Call
		tool.generate(configuration, listener);
		
		// TODO Checks the output
	}

}
