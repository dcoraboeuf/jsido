package net.sf.sido.gen.support;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import net.sf.sido.gen.GenerationInput;
import net.sf.sido.parser.NamedInput;
import net.sf.sido.parser.discovery.SidoDiscovery;

public class FileGenerationInput implements GenerationInput  {

	private final File file;

	public FileGenerationInput(File file) {
		this.file = file;
	}
	
	@Override
	public NamedInput getNamedInput() throws IOException {
		String content = FileUtils.readFileToString(file, SidoDiscovery.SIDO_ENCODING);
		return new NamedInput(file.getPath(), content);
	}

}
