package net.sf.sido.parser.support;

import net.sf.jstring.Localizable;
import net.sf.jstring.MultiLocalizable;
import net.sf.sido.parser.SidoParser;
import net.sf.sido.parser.model.XSchema;
import net.sf.sido.parser.parboiled.XAction;
import net.sf.sido.parser.parboiled.XParser;
import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;

import org.parboiled.Parboiled;
import org.parboiled.buffers.DefaultInputBuffer;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.errors.ParseError;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class DefaultSidoParser implements SidoParser {
	
	private final SidoContext context;
	
	public DefaultSidoParser(SidoContext context) {
		this.context = context;
	}
	
	@Override
	public SidoContext getContext() {
		return context;
	}

	@Override
	public SidoSchema parse(String input) {
		return parse (new DefaultInputBuffer(input.toCharArray()));
	}
	
	protected SidoSchema parse (InputBuffer buffer) {
		// Parses the definition
		XSchema xSchema = xparse(buffer);
		// Extracts the schema
		return buildSchema (xSchema);
	}

	protected XSchema xparse (InputBuffer buffer) {
		// Creates the action support
		XAction action = new XAction();
		// Creates the parser
		XParser parser = Parboiled.createParser(XParser.class, action);
		// Creates the parser runner
		ParseRunner<String> runner = new ReportingParseRunner<String>(parser.schema());
		// Runs the parser
		ParsingResult<String> result = runner.run(buffer);
		// Checks the result
		if (result.hasErrors()) {
			MultiLocalizable list = new MultiLocalizable(Collections2.transform(result.parseErrors, new Function<ParseError, Localizable>() {
				@Override
				public Localizable apply(ParseError error) {
					return localize (error);
				}
			}));
			throw new SidoParseException(list);
		}
		// OK
		else {
			// Gets the X schema
			XSchema xSchema = action.getSchema();
			// OK
			return xSchema;
		}
	}
	
	protected Localizable localize(ParseError error) {
		return new SidoParseExceptionDetail(error.getStartIndex(), error.getEndIndex(), error.getErrorMessage());
	}

	protected SidoSchema buildSchema(XSchema xSchema) {
		return SchemaBuilder.create(context).build(xSchema).get();
	}

}
