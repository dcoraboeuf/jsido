package sido.test;

public class Person {

	private long id = 0L;
	private Integer age;
	private String name = "";

	public long getId () {
		return id;
	}

	public void setId (long pValue) {
		id = pValue;
	}

	public Integer getAge () {
		return age;
	}

	public void setAge (Integer pValue) {
		age = pValue;
	}

	public String getName () {
		return name;
	}

	public void setName (String pValue) {
		name = pValue;
	}

}
