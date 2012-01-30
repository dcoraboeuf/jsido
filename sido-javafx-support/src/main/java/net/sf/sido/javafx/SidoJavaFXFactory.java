package net.sf.sido.javafx;

import java.util.ArrayList;

import net.sf.sido.collections.support.IndexedLists;

import com.google.common.base.Function;

public class SidoJavaFXFactory {
	
	public static <T, K> ObservableIndexedList<T, K> observableIndexedList(String name) {
		return observableIndexedList(IndexedLists.<T,K>propertyFunction(name));
	}

	public static <T, K> ObservableIndexedList<T, K> observableIndexedList(Function<T, K> indexer) {
		return new BasicObservableIndexedList<T,K>(new ArrayList<T>(), indexer);
	}

}
