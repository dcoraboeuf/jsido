package net.sf.sido.collections.support;

import java.util.List;

import net.sf.sido.collections.IndexedList;

import com.google.common.base.Function;

public class IndexedLists {
	
	public static <T, K> IndexedList<T, K> indexedList (List<T> support, Function<T, K> indexer) {
		return new BasicIndexedList<T, K>(support, indexer);
	}

}
