package net.sf.sido.gen.support;

import java.util.Collection;

import net.sf.sido.gen.GenerationConfiguration;
import net.sf.sido.gen.GenerationInput;
import net.sf.sido.gen.model.GenerationOutput;
import net.sf.sido.gen.model.Options;

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
	
	public GenerationConfigurationBuilder output (GenerationOutput output) {
		configuration.setOutput(output);
		return this;
	}
	
	public GenerationConfigurationBuilder options (Options options) {
		configuration.setOptions(options);
		return this;
	}
	
	public GenerationConfigurationBuilder registrationOutput (GenerationOutput output) {
		configuration.setRegistrationOutput(output);
		return this;
	}
	
	public GenerationConfiguration build() {
		return configuration;
	}

}
