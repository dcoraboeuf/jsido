package net.sf.sido.gen.model.support;

import net.sf.sido.gen.model.GenerationModel;
import net.sf.sido.gen.model.GenerationResult;

public abstract class AbstractGenerationModel<R extends GenerationResult> implements GenerationModel<R> {

	private final String id;

	public AbstractGenerationModel(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}

}
