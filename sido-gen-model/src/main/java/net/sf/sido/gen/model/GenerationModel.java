package net.sf.sido.gen.model;

import net.sf.sido.schema.SidoType;

public interface GenerationModel {
	
	String getId();

	GenerationResult createResultInstance();

	void generate(GenerationResult result, SidoType type,
			GenerationContext generationContext, GenerationListener listener);

}
