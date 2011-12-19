package net.sf.sido.gen;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.apache.commons.lang3.StringUtils;

import net.sf.sido.gen.model.GenerationModel;

public class GenerationTool {
	
	public void generate (GenerationConfiguration configuration, GenerationListener listener) {
		// Searches for the generation model
		GenerationModel generationModel = lookupGenerationModel(configuration, listener);
		listener.log("Using generator: %s --> %s", configuration.getModelId(), generationModel);
		// FIXME Generation
	}

	protected GenerationModel lookupGenerationModel(
			GenerationConfiguration configuration, GenerationListener listener) {
		String modelId = configuration.getModelId();
		listener.log("Looking for model generator [%s]", modelId);
		ServiceLoader<GenerationModel> loader = ServiceLoader.load(GenerationModel.class, Thread.currentThread().getContextClassLoader());
		Iterator<GenerationModel> i = loader.iterator();
		GenerationModel modelGenerator = null;
		while (i.hasNext()) {
			GenerationModel generator = i.next();
			listener.log("Found generator: %s --> %s", generator.getId(), generator);
			if (StringUtils.equals(modelId, generator.getId())) {
				modelGenerator = generator;
			}
		}
		if (modelGenerator == null) {
			throw new RuntimeException("Cannot find any generator for model " + modelId);
		} else {
			return modelGenerator;
		}
	}

}
