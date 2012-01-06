package net.sf.sido.gen.cli;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class Main {

	/**
	 * Entry point.
	 * 
	 * @param args
	 *            CLI arguments
	 */
	public static void main(String[] args) {
		// Options
		Options options = readOptions(args);
		// Summary
		summary(options);
	}

	private static void summary(Options options) {
		if (options.verbose) {
			System.out.format("Model: %s%n", options.model);
			System.out.format("Source: %s%n", options.sourceDirectory);
			System.out.format("Output: %s%n", options.outputDirectory);
			if (options.options.isEmpty()) {
				System.out.format("No option%n");
			} else {
				System.out.format("Options:%n");
				for (Map.Entry<String, String> entry : options.options.entrySet()) {
					System.out.format(" * %s = %s%n", entry.getKey(), entry.getValue());
				}
			}
		}
	}

	private static Options readOptions(String[] args) {
		// Instance
		Options options = new Options();
		// Parsing
		CmdLineParser parser = new CmdLineParser(options);
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			System.err.format("Problem with the arguments:%n%s%n%s%n%n", StringUtils.join(args, " "), e.getMessage());
			System.err.println("Usage:");
			parser.printUsage(System.err);
			System.exit(-1);
		}
		// OK
		return options;
	}

}
