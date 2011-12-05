package net.sf.sido.parser.actions;

import net.sf.sido.schema.builder.SchemaBuilder;

import org.parboiled.Action;
import org.parboiled.Context;
import org.parboiled.ContextAware;

public class SidoParsingAction implements Action<Object>, ContextAware<Object> {
	
	private Context<Object> context;
	private SchemaBuilder schemaBuilder;

	@Override
	public void setContext(Context<Object> context) {
		this.context = context;
	}

	@Override
	public boolean run(Context<Object> context) {
		return false;
	}

}
