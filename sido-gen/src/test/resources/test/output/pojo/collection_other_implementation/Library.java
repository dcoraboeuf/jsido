package sido.test;

import java.util.HashSet;
import java.util.Set;

public class Library {

	private Set<Book> books = new HashSet<Book>();

	public Set<Book> getBooks () {
		return books;
	}

	public void addBooks (Book... pValues) {
		for (Book pValue : pValues) {
			books.add(pValue);
		}
	}

	public void setBooks (Set<Book> pValues) {
		books = pValues;
	}

}
