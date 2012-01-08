package net.sf.sido.gen.model.support;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sf.sido.gen.model.GenerationOutput;

import com.google.common.base.Functions;
import com.google.common.collect.Maps;

public class RecordingGenerationOutput implements GenerationOutput {
	
	private final Map<String, StringWriter> writers = new HashMap<String, StringWriter>();

	@Override
	public PrintWriter createInPackage(String packageName, String fileName)
			throws IOException {
		StringWriter writer = new StringWriter();
		String key = packageName + "." + fileName;
		key = StringUtils.strip(key, ".");
		writers.put(key, writer);
		return new PrintWriter(writer);
	}

	public Map<String, String> getFiles() {
		return Maps.transformValues(writers, Functions.toStringFunction());
	}

}
