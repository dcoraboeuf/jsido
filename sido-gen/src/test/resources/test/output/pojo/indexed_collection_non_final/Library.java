package sido.test;

import java.util.Arrays;
import net.sf.sido.collections.IndexedList;
import net.sf.sido.collections.support.IndexedLists;

public class Library {

	private IndexedList<Book,Long> books = IndexedLists.indexedList("id");

	public IndexedList<Book,Long> getBooks () {
		return books;
	}

	public Book getBookById (long pKey) {
		return books.getByIndex(pKey);
	}

	public void addBooks (Book... pValues) {
		books.addAll(Arrays.asList(pValues));
	}

	public void setBooks (IndexedList<Book,Long> pValues) {
		books = pValues;
	}

}
