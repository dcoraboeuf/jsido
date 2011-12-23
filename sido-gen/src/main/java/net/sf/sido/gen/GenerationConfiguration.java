package net.sf.sido.gen;

import java.util.Collection;

import net.sf.sido.gen.model.GenerationOutput;

import org.apache.commons.lang3.Validate;

public class GenerationConfiguration {

	private String modelId;
	private Collection<GenerationInput> inputs;
	private GenerationOutput output;

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

	public GenerationOutput getOutput() {
		return output;
	}

	public void setOutput(GenerationOutput output) {
		this.output = output;
	}

	public void validate() {
		Validate.notBlank(modelId, "The model ID is required");
		Validate.notEmpty(inputs, "The list of inputs is required");
		Validate.notNull(output, "The output directory is required");
	}

}
