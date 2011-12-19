package net.sf.sido.gen.model.support;

import net.sf.sido.gen.model.GenerationModel;

public abstract class AbstractGenerationModel implements GenerationModel {

	private final String id;

	public AbstractGenerationModel(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}

}
