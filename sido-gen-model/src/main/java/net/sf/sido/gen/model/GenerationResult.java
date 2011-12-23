package net.sf.sido.gen.model;

import java.io.IOException;

public interface GenerationResult {
	
	void write (GenerationOutput generationOutput, GenerationListener listener) throws IOException;

}
