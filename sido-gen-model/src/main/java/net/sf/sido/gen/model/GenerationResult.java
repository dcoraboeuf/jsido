package net.sf.sido.gen.model;

import java.io.File;
import java.io.IOException;

public interface GenerationResult {
	
	void write (File output, GenerationListener listener) throws IOException;

}
