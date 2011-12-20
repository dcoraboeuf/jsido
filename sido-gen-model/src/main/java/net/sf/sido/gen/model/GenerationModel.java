package net.sf.sido.gen.model;

import net.sf.sido.schema.SidoType;

public interface GenerationModel<R extends GenerationResult> {
	
	String getId();

	R createResultInstance();

	void generate(R result, SidoType type,
			GenerationContext generationContext, GenerationListener listener);

}
