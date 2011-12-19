package net.sf.sido.gen.support;

import net.sf.sido.gen.GenerationConfiguration;

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
	
	public GenerationConfiguration build() {
		return configuration;
	}

}
