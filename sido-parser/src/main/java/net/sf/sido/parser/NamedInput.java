package net.sf.sido.parser;

public class NamedInput {

	private final String name;
	private final String input;

	public NamedInput(String name, String input) {
		this.name = name;
		this.input = input;
	}

	public String getName() {
		return name;
	}

	public String getInput() {
		return input;
	}

	@Override
	public String toString() {
		return name;
	}

}
