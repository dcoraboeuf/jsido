package net.sf.sido.gen;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.GenerationListener;
import net.sf.sido.gen.model.GenerationModel;
import net.sf.sido.gen.model.GenerationOutput;
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
import net.sf.sido.schema.support.DefaultSidoContext;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class GenerationTool {

	protected static class InputSchema {

		public static final Function<InputSchema, SidoSchema> getSchemaFn = new Function<InputSchema, SidoSchema>() {
			
			public SidoSchema apply(InputSchema o) {
				return o.getSchema();
			}
			
		};
		
		private final NamedInput input;
		private final SidoSchema schema;

		public InputSchema(NamedInput input, SidoSchema schema) {
			this.input = input;
			this.schema = schema;
		}

		public NamedInput getInput() {
			return input;
		}

		public SidoSchema getSchema() {
			return schema;
		}

	}

	public void generate(GenerationConfiguration configuration, GenerationListener listener) throws IOException {
		// Validation
		configuration.validate();
		// Searches for the generation model
		GenerationModel<? extends GenerationResult> generationModel = lookupGenerationModel(configuration, listener);
		listener.log("Using generator: %s --> %s", configuration.getModelId(), generationModel);
		// Context
		SidoContext formerContext = Sido.setContext(new DefaultSidoContext());
		try {
			// Generation
			generate(configuration, listener, generationModel);
		} finally {
			Sido.setContext(formerContext);
		}
	}

	protected <R extends GenerationResult> void generate(GenerationConfiguration configuration,
			GenerationListener listener, GenerationModel<R> generationModel) throws IOException {
		// Gets the context
		SidoContext context = Sido.getContext();
		// Discovers all other schemas
		loadExistingSchemas(context, configuration, listener);
		// Loads all schemas to generate
		Collection<InputSchema> inputSchemas = loadSchemasToGenerate(context, configuration, listener);
		// Generation context
		GenerationContext generationContext = new GenerationContext(configuration.getOptions());
		// Generation
		Collection<SidoSchema> schemas = Collections2.transform(inputSchemas, InputSchema.getSchemaFn);
		R result = generateAll(schemas, generationModel, generationContext, listener);
		// Writes the result down
		result.write(configuration.getOutput(), listener);
		// Writes the registration down
		if (configuration.mustWriteRegistration()) {
			writeRegistration(inputSchemas, generationModel, configuration, listener);
		}
	}

	protected void writeRegistration(Collection<InputSchema> inputSchemas, GenerationModel<?> generationModel,
			GenerationConfiguration configuration, GenerationListener listener) throws IOException {
		GenerationOutput registrationOutput = configuration.getRegistrationOutput();
		// Index
		PrintWriter index = registrationOutput.createInPackage("", SidoDiscovery.SIDO_SCHEMAS);
		try {
			// For all schemas
			for (InputSchema schema : inputSchemas) {
				index.format("%s\tmodel=%s%n", schema.getSchema().getUid(), generationModel.getId());
			}
		} finally {
			index.close();
		}
		// For all schemas
		for (InputSchema schema : inputSchemas) {
			PrintWriter writer = registrationOutput.createInPackage(
					"",
						String.format(SidoDiscovery.SIDO_SCHEMA_PATH, schema.getSchema().getUid()));
			try {
				// Writes the schema down
				writer.print(schema.getInput().getInput());
			} finally {
				writer.close();
			}
		}
	}

	protected <R extends GenerationResult> R generateAll(Collection<SidoSchema> schemas,
			GenerationModel<R> generationModel, GenerationContext generationContext, GenerationListener listener) {
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
			GenerationModel<R> generationModel, GenerationContext generationContext, GenerationListener listener) {
		listener.log("Generating schema %s", schema.getUid());
		// Generates the types
		for (SidoType type : schema.getTypes()) {
			generateType(result, type, generationModel, generationContext, listener);
		}
	}

	protected <R extends GenerationResult> void generateType(R result, SidoType type,
			GenerationModel<R> generationModel, GenerationContext generationContext, GenerationListener listener) {
		listener.log("Generating type %s", type.getQualifiedName());
		// Calls the model
		generationModel.generate(result, type, generationContext, listener);
	}

	protected Collection<InputSchema> loadSchemasToGenerate(SidoContext context, GenerationConfiguration configuration,
			GenerationListener listener) {
		// List of files
		Collection<GenerationInput> files = configuration.getInputs();
		// Loads the files
		Collection<NamedInput> inputs = Collections2.transform(files, new Function<GenerationInput, NamedInput>() {

			@Override
			public NamedInput apply(GenerationInput file) {
				try {
					return file.getNamedInput();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		// Creates a parser
		SidoParser parser = SidoParserFactory.createParser(context);
		// Parsing
		Collection<SidoSchema> schemas = parser.parse(inputs);
		// Combines inputs and schemas
		Collection<InputSchema> inputSchemas = new ArrayList<InputSchema>();
		Iterator<NamedInput> i1 = inputs.iterator();
		Iterator<SidoSchema> i2 = schemas.iterator();
		while (i1.hasNext()) {
			inputSchemas.add(new InputSchema(i1.next(), i2.next()));
		}
		return inputSchemas;
	}

	protected void loadExistingSchemas(SidoContext context, GenerationConfiguration configuration,
			final GenerationListener listener) {
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
		ServiceLoader<GenerationModel> loader = ServiceLoader.load(GenerationModel.class, Thread
				.currentThread()
					.getContextClassLoader());
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
