package net.sf.sido.gen;

import java.io.File;
import java.util.Collection;

import org.apache.commons.lang3.Validate;

public class GenerationConfiguration {

	private String modelId;
	private Collection<File> files;

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
	
	public void validate() {
		Validate.notBlank(modelId, "The model ID is required");
		Validate.notEmpty(files, "The list of files is required");
	}

}
