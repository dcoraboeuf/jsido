package sido.test;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Node {

	private final SimpleStringProperty name = new SimpleStringProperty(this, "name", "");
	private final SimpleObjectProperty<Node> parent = new SimpleObjectProperty<Node>(this, "parent");

	public final SimpleObjectProperty<Node> parentProperty () {
		return parent;
	}

	public final Node getParent () {
		return parent.get();
	}

	public final void setParent (Node pValue) {
		parent.set(pValue);
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
