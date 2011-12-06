package net.sf.sido.parser;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;

public interface SidoParser {
	
	SidoContext getContext();
	
	SidoSchema parse (String input);

}
