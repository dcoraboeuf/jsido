package net.sf.sido.gen.model.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import net.sf.sido.gen.java.JClass;
import net.sf.sido.gen.model.GenerationListener;

import org.apache.commons.lang3.StringUtils;

public class JavaGenerationResult extends AbstractGenerationResult {

	private final Set<JClass> classes = new HashSet<JClass>();

	public void addClass(JClass j) {
		classes.add(j);
	}

	@Override
	public void write(File output, GenerationListener listener)
			throws IOException {
		for (JClass jClass : classes) {
			writeClass(jClass, output, listener);
		}
	}

	protected void writeClass(JClass jClass, File output,
			GenerationListener listener) throws IOException {
		String className = jClass.getName();
		String fileName = className + ".java";
		String packagePath = StringUtils.replace(jClass.getPackageName(), ".",
				"/");
		File packageFile = new File(output, packagePath);
		if (!packageFile.exists()) {
			packageFile.mkdirs();
		}
		File file = new File(packageFile, fileName);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(file), "UTF-8"));
		try {
			jClass.write(writer);
		} finally {
			writer.close();
		}
	}

}
