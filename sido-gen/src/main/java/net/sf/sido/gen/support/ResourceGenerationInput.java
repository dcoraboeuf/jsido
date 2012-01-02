package net.sf.sido.gen.support;

import java.io.IOException;
import java.io.InputStream;

import net.sf.sido.gen.GenerationInput;
import net.sf.sido.parser.NamedInput;
import net.sf.sido.parser.discovery.SidoDiscovery;

import org.apache.commons.io.IOUtils;

public class ResourceGenerationInput implements GenerationInput {

	private final String resourcePath;

	public ResourceGenerationInput(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	@Override
	public NamedInput getNamedInput() throws IOException {
		InputStream in = getClass().getResourceAsStream(resourcePath);
		if (in == null) {
			throw new IOException("Cannot find resource at " + resourcePath);
		} else {
			try {
				String content = IOUtils.toString(in,
						SidoDiscovery.SIDO_ENCODING);
				return new NamedInput(resourcePath, content);
			} finally {
				in.close();
			}
		}
	}

}
