package sido.test;

import java.util.ArrayList;
import java.util.List;

public class Library {

	private final List<Book> books = new ArrayList<Book>();

	public List<Book> getBooks () {
		return books;
	}

	public void addBooks (Book... pValues) {
		for (Book pValue : pValues) {
			books.add(pValue);
		}
	}

}
