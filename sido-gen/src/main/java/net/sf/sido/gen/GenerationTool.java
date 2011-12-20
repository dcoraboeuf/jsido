package net.sf.sido.gen;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.GenerationListener;
import net.sf.sido.gen.model.GenerationModel;
import net.sf.sido.gen.model.GenerationResult;
import net.sf.sido.parser.NamedInput;
import net.sf.sido.parser.SidoParser;
import net.sf.sido.parser.SidoParserFactory;
import net.sf.sido.parser.discovery.SidoDiscovery;
import net.sf.sido.parser.discovery.SidoDiscoveryLogger;
import net.sf.sido.parser.discovery.SidoSchemaDiscovery;
import net.sf.sido.parser.discovery.support.DefaultSidoDiscovery;
import net.sf.sido.schema.Sido;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class GenerationTool {
	
	public void generate (GenerationConfiguration configuration, GenerationListener listener) {
		// Validation
		configuration.validate();
		// Searches for the generation model
		GenerationModel<? extends GenerationResult> generationModel = lookupGenerationModel(configuration, listener);
		listener.log("Using generator: %s --> %s", configuration.getModelId(), generationModel);
		generate(configuration, listener, generationModel);
	}

	protected <R extends GenerationResult> void generate(GenerationConfiguration configuration,
			GenerationListener listener,
			GenerationModel<R> generationModel) {
		// Gets the context
		SidoContext context = Sido.getContext();
		// Discovers all other schemas
		loadExistingSchemas(context, configuration, listener);
		// Loads all schemas to generate
		Collection<SidoSchema> schemas = loadSchemasToGenerate(context, configuration, listener);
		// Generation context
		GenerationContext generationContext = new GenerationContext();
		// TODO Context schemas (for packaging lookup)
		// Generation
		@SuppressWarnings("unused")
		R result = generateAll (schemas, generationModel, generationContext, listener);
		// TODO Writes the result down
	}

	protected <R extends GenerationResult> R generateAll(Collection<SidoSchema> schemas,
			GenerationModel<R> generationModel,
			GenerationContext generationContext, GenerationListener listener) {
		// Creates the result
		R result = generationModel.createResultInstance();
		listener.log("Result instance is %s", result);
		// Full generation
		listener.log("Generating all schemas and types");
		for (SidoSchema schema : schemas) {
			generateSchema(result, schema, generationModel, generationContext, listener);
		}
		// OK
		return result;
	}

	protected <R extends GenerationResult> void generateSchema(R result, SidoSchema schema,
			GenerationModel<R> generationModel,
			GenerationContext generationContext, GenerationListener listener) {
		listener.log("Generating schema %s", schema.getUid());
		// Generates the types
		for (SidoType type : schema.getTypes()) {
			generateType(result, type, generationModel, generationContext, listener);
		}
	}

	protected <R extends GenerationResult> void generateType(R result, SidoType type,
			GenerationModel<R> generationModel,
			GenerationContext generationContext, GenerationListener listener) {
		listener.log("Generating type %s", type.getQualifiedName());
		// Calls the model
		generationModel.generate(result, type, generationContext, listener);
	}

	protected Collection<SidoSchema> loadSchemasToGenerate(SidoContext context,
			GenerationConfiguration configuration, GenerationListener listener) {
		// List of files
		Collection<File> files = configuration.getFiles();
		// Loads the files
		Collection<NamedInput> inputs = Collections2.transform(files, new Function<File, NamedInput>() {

			@Override
			public NamedInput apply(File file) {
				try {
					String content = FileUtils.readFileToString(file, SidoDiscovery.SIDO_ENCODING);
					return new NamedInput(file.getPath(), content);
				} catch (IOException ex) {
					throw new RuntimeException("Cannot read file at " + file, ex);
				}
			}
		});
		// Creates a parser
		SidoParser parser = SidoParserFactory.createParser(context);
		// Parsing
		Collection<SidoSchema> schemas = parser.parse(inputs);
		// OK
		return schemas;
	}

	protected void loadExistingSchemas(SidoContext context,
			GenerationConfiguration configuration, final GenerationListener listener) {
		// Discovery engine
		SidoDiscovery discovery = new DefaultSidoDiscovery();
		// Call
		@SuppressWarnings("unused")
		Collection<SidoSchemaDiscovery> discoveryResults = discovery.discover(context, new SidoDiscoveryLogger() {
			
			@Override
			public void log(String format, Object... parameters) {
				listener.log(format, parameters);
			}
		});
	}

	protected <R extends GenerationResult> GenerationModel<R> lookupGenerationModel(
			GenerationConfiguration configuration, GenerationListener listener) {
		String modelId = configuration.getModelId();
		listener.log("Looking for model generator [%s]", modelId);
		@SuppressWarnings("rawtypes")
		ServiceLoader<GenerationModel> loader = ServiceLoader.load(GenerationModel.class, Thread.currentThread().getContextClassLoader());
		@SuppressWarnings("rawtypes")
		Iterator<GenerationModel> i = loader.iterator();
		GenerationModel<? extends GenerationResult> modelGenerator = null;
		while (i.hasNext()) {
			@SuppressWarnings("unchecked")
			GenerationModel<? extends GenerationResult> generator = i.next();
			listener.log("Found generator: %s --> %s", generator.getId(), generator);
			if (StringUtils.equals(modelId, generator.getId())) {
				modelGenerator = generator;
			}
		}
		if (modelGenerator == null) {
			throw new RuntimeException("Cannot find any generator for model " + modelId);
		} else {
			@SuppressWarnings("unchecked")
			GenerationModel<R> generator = (GenerationModel<R>) modelGenerator;
			return generator;
		}
	}

}
