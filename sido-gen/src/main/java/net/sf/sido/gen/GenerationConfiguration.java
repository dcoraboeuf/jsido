package net.sf.sido.gen;

import java.io.File;
import java.util.Collection;

import org.apache.commons.lang3.Validate;

public class GenerationConfiguration {

	private String modelId;
	private Collection<File> files;
	private File output;

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public Collection<File> getFiles() {
		return files;
	}

	public void setFiles(Collection<File> files) {
		this.files = files;
	}

	public File getOutput() {
		return output;
	}

	public void setOutput(File output) {
		this.output = output;
	}

	public void validate() {
		Validate.notBlank(modelId, "The model ID is required");
		Validate.notEmpty(files, "The list of files is required");
		Validate.notNull(output, "The output directory is required");
	}

}
