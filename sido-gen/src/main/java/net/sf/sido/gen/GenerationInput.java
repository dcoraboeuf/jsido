package net.sf.sido.gen;

import java.io.IOException;

import net.sf.sido.parser.NamedInput;

public interface GenerationInput {

	NamedInput getNamedInput() throws IOException;

}
