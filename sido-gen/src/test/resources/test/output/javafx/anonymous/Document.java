package sido.test;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Document {

	private final SimpleObjectProperty<Object> content = new SimpleObjectProperty<Object>(this, "content");
	private final SimpleLongProperty id = new SimpleLongProperty(this, "id", 0L);

	public final SimpleObjectProperty<Object> contentProperty () {
		return content;
	}

	public final Object getContent () {
		return content.get();
	}

	public final void setContent (Object pValue) {
		content.set(pValue);
	}

	public final SimpleLongProperty idProperty () {
		return id;
	}

	public final long getId () {
		return id.get();
	}

	public final void setId (long pValue) {
		id.set(pValue);
	}

}
