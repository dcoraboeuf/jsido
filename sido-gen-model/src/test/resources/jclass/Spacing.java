package sido.test;

import java.util.ArrayList;
import java.util.List;

public class Test {

	private String name;
	private List<String> otherNames = new ArrayList<String>();

	public Test () {
	}

	public Test (String value) {
		this.name = value;
	}

	public String getName () {
		return name;
	}

	public void setName (String value) {
		this.name = value;
	}

}
