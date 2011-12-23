package net.sf.sido.gen.model;

import java.io.IOException;
import java.io.PrintWriter;

public interface GenerationOutput {

	PrintWriter createInPackage(String packageName, String fileName) throws IOException;

}
