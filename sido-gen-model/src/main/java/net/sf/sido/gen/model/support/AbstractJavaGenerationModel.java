package net.sf.sido.gen.model.support;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.GenerationListener;
import net.sf.sido.gen.model.GenerationResult;
import net.sf.sido.schema.SidoType;

public abstract class AbstractJavaGenerationModel extends AbstractGenerationModel {

	public AbstractJavaGenerationModel(String id) {
		super(id);
	}
	
	@Override
	public GenerationResult createResultInstance() {
		return new JavaGenerationResult();
	}
	
	@Override
	public void generate(GenerationResult result, SidoType type,
			GenerationContext generationContext, GenerationListener listener) {
		// TODO Auto-generated method stub
		
	}

}
