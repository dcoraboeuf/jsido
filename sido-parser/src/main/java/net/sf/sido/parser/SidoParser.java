package net.sf.sido.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class SidoParser extends BaseParser<Object> {

	Rule schema() {
		return Sequence(schema_decl(), prefix_list());
	}

	Rule schema_decl() {
		return Sequence("schema", uid_ref(), ".");
	}

	Rule prefix_list() {
		return ZeroOrMore(prefix());
	}

	Rule prefix() {
		return Sequence("uses", id(), "for", uid_ref(), ".");
	}

	Rule uid_ref() {
		return Sequence("<", uid(), ">");
	}

	Rule uid() {
		return Sequence(id(), ZeroOrMore(Sequence(".", id())));
	}

	Rule id() {
		return OneOrMore(id_char());
	}

	Rule id_char() {
		return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'), CharRange('0', '9'), '_');
	}

}
