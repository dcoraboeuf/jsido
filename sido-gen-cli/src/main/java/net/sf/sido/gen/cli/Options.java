package net.sf.sido.gen.cli;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.kohsuke.args4j.Option;

public class Options {
	
	@Option(name = "--verbose", aliases = "-v", usage = "Verbosity of the output")
	public boolean verbose;

	@Option(name = "--model", aliases = "-m", usage = "ID of the generation model", metaVar = "ID")
	public String model = "pojo";

	@Option(name = "--source", aliases = "-s", usage = "Specifies the directory containing SiDOL files", metaVar = "DIR")
	public File sourceDirectory = new File(".");

	@Option(name = "--output", aliases = "-o", usage = "Location for generated Java files", metaVar = "DIR")
	public File outputDirectory = new File(".");

	@Option(name = "--registration", aliases = "-r", usage = "Location for generated resource files", metaVar = "DIR")
	public File registrationDirectory;
	
	@Option(name = "--option", aliases = "-i", usage = "Options for the generation model", metaVar = "OPTION")
	public Map<String, String> options = new HashMap<String, String>();

}
