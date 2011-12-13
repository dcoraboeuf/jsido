package net.sf.sido.parser.parboiled;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class XParser extends BaseParser<String> {
	
	private static final String TERMINATOR = ".";
	
	final XAction action;
	
	public XParser(XAction action) {
		this.action = action;
	}

	public Rule schema() {
		return Sequence(schema_decl(), whitespaces0(), prefix_list(), whitespaces0(), type_list(), EOI);
	}

	Rule schema_decl() {
		return Sequence("schema", whitespaces(), uid_ref(), action.schema(/* uid */ pop()), whitespaces0(), TERMINATOR);
	}

	Rule prefix_list() {
		return ZeroOrMore(prefix(), whitespaces0());
	}

	Rule prefix() {
		return Sequence("uses", whitespaces(), id(), push(match()), whitespaces(), "for", whitespaces(), uid_ref(), action.prefix(/* uid */ pop(), /* id */ pop()), whitespaces0(), TERMINATOR);
	}
	
	Rule type_list() {
		return ZeroOrMore(type(), whitespaces0());
	}

	Rule type() {
		return Sequence (type_decl(), whitespaces(), type_header(), whitespaces0(), TERMINATOR);
	}
	
	Rule type_header() {
		return FirstOf(type_with_meta(), type_no_meta());
	}
	
	Rule type_with_meta() {
		return Sequence ("is", whitespaces(), type_meta(), whitespaces0(), ZeroOrMore(",", whitespaces0(), type_meta()), whitespaces0(), "and", whitespaces0(), type_no_meta());
	}
	
	Rule type_no_meta() {
		return Sequence ("has", whitespaces(), type_properties());
	}
	
	Rule type_decl() {
		return Sequence (a(), whitespaces(), id(), action.type(match()));
	}
	
	Rule type_meta() {
		return FirstOf(type_abstract(), type_parent());
	}
	
	Rule type_abstract() {
		return Sequence("abstract", action.abstractType());
	}
	
	Rule type_parent() {
		return Sequence(a(), whitespaces(), type_ref(), action.parent(match()));
	}
	
	Rule type_properties() {
		return Sequence (type_property(), ZeroOrMore(whitespaces0(), ",", whitespaces0(), type_property()));
	}
	
	Rule type_property() {
		return Sequence (a(), action.propertyStart(), whitespaces(), property(), action.propertyFinish());
	}
	
	Rule property() {
		return FirstOf(property_collection(), property_type(), property_no_type());
	}
	
	Rule property_collection() {
		return Sequence (Optional(property_type_nullable()), whitespaces0(), "collection", whitespaces(), action.propertyArray(), "of", whitespaces(), property_type_ref(), whitespaces(), "as", whitespaces(), id(), action.propertyName(match()), Optional(Sequence(whitespaces(), property_array_index())));
	}
	
	Rule property_array_index() {
		return Sequence ("indexed", whitespaces(), "by", whitespaces(), id(), action.propertyIndex(match()));
	}
	
	Rule property_type() {
		return Sequence(Optional(property_type_nullable()), whitespaces0(), property_type_ref(), whitespaces(), "as", whitespaces(), id(), action.propertyName(match()));
	}
	
	Rule property_type_nullable() {
		return Sequence("nullable", action.propertyNullable());
	}

	Rule property_no_type() {
		return Sequence (id(), action.propertyName(match()), action.propertyNoType());
	}
	
	Rule property_type_ref() {
		return FirstOf(property_type_anonymous(), Sequence(type_ref(), action.propertyType(match())));
	}
	
	Rule property_type_anonymous() {
		return Sequence ("anonymous", action.propertyAnonymous());
	}
	
	Rule type_ref() {
		return Sequence(id(), Optional (Sequence ("::", id())));
	}

	Rule uid_ref() {
		return Sequence("<", whitespaces0(), uid(), push(match()), whitespaces0(), ">");
	}

	Rule uid() {
		return Sequence(id(), ZeroOrMore(Sequence(TERMINATOR, id())));
	}
	
	Rule a() {
		return FirstOf("an", "a");
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
