package sido.test;

import java.util.Arrays;
import net.sf.sido.javafx.ObservableIndexedList;
import net.sf.sido.javafx.SidoJavaFXFactory;

public class Library {

	private final ObservableIndexedList<Book,Long> books = SidoJavaFXFactory.observableIndexedList("id");

	public ObservableIndexedList<Book,Long> getBooks () {
		return books;
	}

	public Book getBookById (long pKey) {
		return books.getByIndex(pKey);
	}

	public void addBooks (Book... pValues) {
		books.addAll(Arrays.asList(pValues));
	}

}
