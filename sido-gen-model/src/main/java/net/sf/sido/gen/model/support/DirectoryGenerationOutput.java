package net.sf.sido.gen.model.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import net.sf.sido.gen.model.GenerationOutput;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class DirectoryGenerationOutput implements GenerationOutput {

	private final File root;

	public DirectoryGenerationOutput(File root) {
		this.root = root;
	}

	@Override
	public PrintWriter createInPackage(String packageName, String fileName) throws IOException {
		String packagePath = StringUtils.replace(packageName, ".", "/");
		File packageFile = new File(root, packagePath);
		if (!packageFile.exists()) {
			packageFile.mkdirs();
		}
		File file = new File(packageFile, fileName);
		FileUtils.forceMkdir(file.getParentFile());
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(file), "UTF-8"));
		return writer;
	}

}
