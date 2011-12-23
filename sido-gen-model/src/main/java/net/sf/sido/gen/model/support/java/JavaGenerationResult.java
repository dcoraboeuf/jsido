package net.sf.sido.gen.model.support.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import net.sf.sido.gen.model.GenerationListener;
import net.sf.sido.gen.model.GenerationOutput;
import net.sf.sido.gen.model.support.AbstractGenerationResult;

public class JavaGenerationResult extends AbstractGenerationResult {

	private final Set<JClass> classes = new HashSet<JClass>();

	public void addClass(JClass j) {
		classes.add(j);
	}

	@Override
	public void write(GenerationOutput output, GenerationListener listener)
			throws IOException {
		for (JClass jClass : classes) {
			writeClass(jClass, output, listener);
		}
	}

	protected void writeClass(JClass jClass, GenerationOutput output,
			GenerationListener listener) throws IOException {
		PrintWriter writer = output.createInPackage(jClass.getPackageName(),
				jClass.getName() + ".java");
		try {
			jClass.write(writer);
		} finally {
			writer.close();
		}
	}

}
