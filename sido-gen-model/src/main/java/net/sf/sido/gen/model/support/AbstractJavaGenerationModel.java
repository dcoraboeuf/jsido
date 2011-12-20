package net.sf.sido.gen.model.support;

import net.sf.sido.gen.model.GenerationResult;

public abstract class AbstractJavaGenerationModel extends AbstractGenerationModel {

	public AbstractJavaGenerationModel(String id) {
		super(id);
	}
	
	@Override
	public GenerationResult createResultInstance() {
		return new JavaGenerationResult();
	}

}
