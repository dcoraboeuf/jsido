package sido.test;

public class Person {

	private Long id = 0L;
	private Integer age;
	private String name = "";

	public Long getId () {
		return id;
	}

	public void setId (Long pValue) {
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
