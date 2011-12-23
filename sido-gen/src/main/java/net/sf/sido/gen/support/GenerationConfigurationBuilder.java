package net.sf.sido.gen.support;

import java.io.File;
import java.util.Collection;

import net.sf.sido.gen.GenerationConfiguration;
import net.sf.sido.gen.GenerationInput;

public class GenerationConfigurationBuilder {
	
	public static GenerationConfigurationBuilder create() {
		return new GenerationConfigurationBuilder();
	}

	private final GenerationConfiguration configuration;
	
	protected GenerationConfigurationBuilder() {
		this.configuration = new GenerationConfiguration();
	}
	
	public GenerationConfigurationBuilder modelId(String modelId) {
		configuration.setModelId(modelId);
		return this;
	}
	
	public GenerationConfigurationBuilder sources (Collection<GenerationInput> sources) {
		configuration.setInputs(sources);
		return this;
	}
	
	public GenerationConfigurationBuilder output (File output) {
		configuration.setOutput(output);
		return this;
	}
	
	public GenerationConfiguration build() {
		return configuration;
	}

}
