package sido.test;

public class Person {

	private long id = 0L;
	private Integer age;
	private String name = "";

	public long getId () {
		return id;
	}

	public Person setId (long pValue) {
		id = pValue;
		return this;
	}

	public Integer getAge () {
		return age;
	}

	public Person setAge (Integer pValue) {
		age = pValue;
		return this;
	}

	public String getName () {
		return name;
	}

	public Person setName (String pValue) {
		name = pValue;
		return this;
	}

}
