package net.sf.sido.javafx;

import java.util.List;
import java.util.Map;

import javafx.collections.ListChangeListener;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.sun.javafx.collections.ObservableListWrapper;

public class BasicObservableIndexedList<T,K> extends ObservableListWrapper<T> implements ObservableIndexedList<T, K> {

	private final Function<T, K> indexer;
	private Map<K, T> index;
	
	public BasicObservableIndexedList (List<T> support, Function<T,K> indexer) {
		super(support);
		this.indexer = indexer;
		this.index = Maps.uniqueIndex(support, indexer);
		addListener(new ListChangeListener<T>(){

			@Override
			public void onChanged(
					ListChangeListener.Change<? extends T> change) {
				reindex();
			}
			
		});
	}

	protected void reindex() {
		index = Maps.uniqueIndex(this, indexer);
	}

	@Override
	public T getByIndex(K key) {
		return index.get(key);
	}
	
}