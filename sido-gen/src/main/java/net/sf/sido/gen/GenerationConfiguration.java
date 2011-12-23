package net.sf.sido.gen;

import java.io.File;
import java.util.Collection;

import org.apache.commons.lang3.Validate;

public class GenerationConfiguration {

	private String modelId;
	private Collection<GenerationInput> inputs;
	private File output;

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public Collection<GenerationInput> getInputs() {
		return inputs;
	}

	public void setInputs(Collection<GenerationInput> inputs) {
		this.inputs = inputs;
	}

	public File getOutput() {
		return output;
	}

	public void setOutput(File output) {
		this.output = output;
	}

	public void validate() {
		Validate.notBlank(modelId, "The model ID is required");
		Validate.notEmpty(inputs, "The list of inputs is required");
		Validate.notNull(output, "The output directory is required");
	}

}
