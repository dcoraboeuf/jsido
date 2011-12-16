package net.sf.sido.collections;

import java.util.List;

public interface IndexedList<T, K> extends List<T> {
	
	T getByIndex (K key);

}
