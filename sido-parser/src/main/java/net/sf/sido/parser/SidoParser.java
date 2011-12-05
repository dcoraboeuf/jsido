package net.sf.sido.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class SidoParser extends BaseParser<Object> {

	Rule schema() {
		return Sequence(schema_decl(), whitespaces0(), prefix_list(), whitespaces0());
	}

	Rule schema_decl() {
		return Sequence("schema", whitespaces(), uid_ref(), whitespaces0(), ".");
	}

	Rule prefix_list() {
		return ZeroOrMore(prefix());
	}

	Rule prefix() {
		return Sequence("uses", id(), "for", uid_ref(), ".");
	}

	Rule uid_ref() {
		return Sequence("<", whitespaces0(), uid(), whitespaces0(), ">");
	}

	Rule uid() {
		return Sequence(id(), ZeroOrMore(Sequence(".", id())));
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
