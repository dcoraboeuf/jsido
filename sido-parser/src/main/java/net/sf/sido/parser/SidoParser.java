package net.sf.sido.parser;

import net.sf.sido.parser.actions.SidoParsingAction;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class SidoParser extends BaseParser<String> {
	
	private static final String TERMINATOR = ".";
	
	final SidoParsingAction action;
	
	public SidoParser(SidoParsingAction action) {
		this.action = action;
	}

	Rule schema() {
		return Sequence(schema_decl(), whitespaces0(), prefix_list(), whitespaces0(), type_list());
	}

	Rule schema_decl() {
		return Sequence("schema", whitespaces(), uid_ref(), action.schema(match()), whitespaces0(), TERMINATOR);
	}

	Rule prefix_list() {
		return ZeroOrMore(prefix(), whitespaces0());
	}

	Rule prefix() {
		return Sequence("uses", whitespaces(), id(), push(match()), whitespaces(), "for", whitespaces(), uid_ref(), action.prefix(pop(), match()), whitespaces0(), TERMINATOR);
	}
	
	Rule type_list() {
		return ZeroOrMore(type(), whitespaces0());
	}

	Rule type() {
		return Sequence (a(), whitespaces(), id(), action.type(match()), TERMINATOR);
	}
	
	Rule uid_ref() {
		return Sequence("<", whitespaces0(), uid(), whitespaces0(), ">");
	}

	Rule uid() {
		return Sequence(id(), ZeroOrMore(Sequence(TERMINATOR, id())));
	}
	
	Rule a() {
		return FirstOf("a", "an");
	}

	Rule id() {
		return OneOrMore(id_char());
	}

	Rule id_char() {
		return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'),
				CharRange('0', '9'), '_');
	}

	Rule whitespaces() {
		return OneOrMore(whitespace());
	}

	Rule whitespaces0() {
		return ZeroOrMore(whitespace());
	}

	Rule whitespace() {
		return AnyOf(" \t\f\r\n");
	}

}
