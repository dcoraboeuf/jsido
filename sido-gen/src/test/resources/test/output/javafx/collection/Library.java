package sido.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Library {

	private final ObservableList<Book> books = FXCollections.observableArrayList();

	public ObservableList<Book> getBooks () {
		return books;
	}

	public void addBooks (Book... pValues) {
		books.addAll (pValues);
	}

}
