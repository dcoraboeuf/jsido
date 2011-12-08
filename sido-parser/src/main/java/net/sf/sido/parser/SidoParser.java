package net.sf.sido.parser;

import java.util.Collection;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;

public interface SidoParser {
	
	SidoContext getContext();
	
	Collection<SidoSchema> parse (Collection<NamedInput> inputs);

}
