package net.sf.sido.parser.support;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jstring.Localizable;
import net.sf.jstring.MultiLocalizable;
import net.sf.jstring.NonLocalizable;
import net.sf.sido.parser.NamedInput;
import net.sf.sido.parser.SidoParser;
import net.sf.sido.parser.model.XSchema;
import net.sf.sido.parser.parboiled.XAction;
import net.sf.sido.parser.parboiled.XParser;
import net.sf.sido.parser.support.builder.Builder;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;

import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.buffers.DefaultInputBuffer;
import org.parboiled.errors.InvalidInputError;
import org.parboiled.errors.ParseError;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.MatcherPath;
import org.parboiled.support.ParsingResult;
import org.parboiled.support.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class DefaultSidoParser implements SidoParser {
	
	private final Logger logger = LoggerFactory.getLogger(SidoParser.class);
	
	private final SidoContext context;
	
	public DefaultSidoParser(SidoContext context) {
		this.context = context;
	}
	
	@Override
	public SidoContext getContext() {
		return context;
	}
	
	@Override
	public Collection<SidoSchema> parse(Collection<NamedInput> inputs) {
		// Parses all inputs
		Collection<XSchema> xSchemas = new ArrayList<XSchema>();
		for (NamedInput input : inputs) {
			XSchema xschema = xparse(input);
			xSchemas.add(xschema);
		}
		// Creation of schemas
		return build (xSchemas);
	}

	protected XSchema xparse (NamedInput namedInput) {
		String input = namedInput.getInput();
		// Input buffer
		DefaultInputBuffer inputBuffer = new DefaultInputBuffer(input.toCharArray());
		// Creates the action support
		XAction action = new XAction();
		// Creates the parser
		XParser parser = Parboiled.createParser(XParser.class, action);
		// Creates the parser runner
		ParseRunner<String> runner = createParserRunner(parser);
		// Runs the parser
		ParsingResult<String> result = runner.run(inputBuffer);
		// Checks the result
		if (result.hasErrors()) {
			MultiLocalizable list = new MultiLocalizable(Collections2.transform(result.parseErrors, new Function<ParseError, Localizable>() {
				@Override
				public Localizable apply(ParseError error) {
					return localize (error);
				}
			}));
			throw new SidoParseException(namedInput.getName(), list);
		}
		// OK
		else {
			// Gets the X schema
			XSchema xSchema = action.getSchema();
			// OK
			return xSchema;
		}
	}

	protected ParseRunner<String> createParserRunner(XParser parser) {
		Rule root = parser.schema();
		if (logger.isDebugEnabled()) {
			return new TracingParseRunner<String>(root);
		} else {
			return new ReportingParseRunner<String>(root);
		}
	}
	
	protected Localizable localize(ParseError error) {
		String match = match(error);
		Position position = getPosition(error);
		int line = position.line;
		int start = position.column;
		int end = start + error.getEndIndex() - error.getStartIndex();
		if (error instanceof InvalidInputError) {
			InvalidInputError inputError = (InvalidInputError) error;
			MultiLocalizable failedMatchers = new MultiLocalizable(
					Collections2.transform(
							inputError.getFailedMatchers(),
							new Function<MatcherPath, Localizable> () {
								@Override
								public Localizable apply(MatcherPath matcherPath) {
									return localize(matcherPath);
								}
							}
					)
			);
			return new SidoParseInvalidInputException(match, line, start, end, failedMatchers);
		} else {
			return new SidoParseExceptionDetail(match, line, start, end, error.getErrorMessage());
		}
	}

	protected String match(ParseError error) {
		return error.getInputBuffer().extract(error.getStartIndex(), error.getEndIndex());
	}

	protected Position getPosition(ParseError error) {
		return error.getInputBuffer().getPosition(error.getStartIndex());
	}

	protected Localizable localize(MatcherPath matcherPath) {
		String path = matcherPath.toString();
		return new NonLocalizable(path);
	}

	protected Collection<SidoSchema> build(Collection<XSchema> xSchemas) {
		return Builder.create(context).build(xSchemas);
	}

}
