package net.sf.sido.parser;

import net.sf.sido.parser.support.DefaultSidoParser;
import net.sf.sido.schema.Sido;
import net.sf.sido.schema.SidoContext;


public class SidoParserFactory {

	public static SidoParser createParser() {
		return createParser(Sido.getContext());
	}
	
	public static SidoParser createParser(SidoContext context) {
		return new DefaultSidoParser(context);
	}

}
