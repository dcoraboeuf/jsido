package net.sf.sido.parser.actions;

import net.sf.sido.schema.builder.SchemaBuilder;
import net.sf.sido.schema.builder.TypeBuilder;

import org.apache.commons.lang3.StringUtils;
import org.parboiled.Action;
import org.parboiled.Context;

public class SidoParsingAction implements Action<Object> {
	
	private SchemaBuilder schemaBuilder;
	private TypeBuilder typeBuilder;
	
	public SchemaBuilder getSchemaBuilder() {
		return schemaBuilder;
	}

	@Override
	public boolean run(Context<Object> context) {
		return false;
	}

	public boolean schema(String uriRef) {
		String uri = parseUri (uriRef);
		schemaBuilder = SchemaBuilder.create(uri);
		return true;
	}

	protected String parseUri(String uriRef) {
		return StringUtils.strip(uriRef, "< >");
	}

	public boolean trace(String match) {
		System.out.format("[sido] %s%n", match);
		return true;
	}

	public boolean prefix(String prefix, String uriRef) {
		schemaBuilder.addPrefix(prefix, parseUri(uriRef));
		return true;
	}

	public boolean type(String name) {
		typeBuilder = schemaBuilder.addType(name);
		return true;
	}

}
