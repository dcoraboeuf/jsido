package sido.test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {

	private final SimpleLongProperty id = new SimpleLongProperty(this, "id", 0L);
	private final SimpleIntegerProperty age = new SimpleIntegerProperty(this, "age");
	private final SimpleStringProperty name = new SimpleStringProperty(this, "name", "");

	public final SimpleLongProperty idProperty () {
		return id;
	}

	public final long getId () {
		return id.get();
	}

	public final void setId (long pValue) {
		id.set(pValue);
	}

	public final SimpleIntegerProperty ageProperty () {
		return age;
	}

	public final Integer getAge () {
		return age.get();
	}

	public final void setAge (Integer pValue) {
		age.set(pValue);
	}

	public final SimpleStringProperty nameProperty () {
		return name;
	}

	public final String getName () {
		return name.get();
	}

	public final void setName (String pValue) {
		name.set(pValue);
	}

}
