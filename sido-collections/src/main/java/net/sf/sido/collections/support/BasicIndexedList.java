package net.sf.sido.collections.support;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import net.sf.sido.collections.IndexedList;

import org.apache.commons.lang3.Validate;

import com.google.common.base.Function;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.Maps;

public class BasicIndexedList<T, K> extends ForwardingList<T> implements
		IndexedList<T, K> {

	private final List<T> support;
	private final Function<T, K> indexer;

	private Map<K, T> index;
	private final ReadWriteLock indexLock = new ReentrantReadWriteLock();

	public BasicIndexedList(List<T> support, Function<T, K> indexer) {
		Validate.notNull(support, "Support list is required");
		Validate.notNull(indexer, "Indexer function is required");
		this.support = support;
		this.indexer = indexer;
		this.index = Maps.uniqueIndex(this.support, indexer);
	}

	@Override
	protected List<T> delegate() {
		return support;
	}

	public T getByIndex(K key) {
		indexLock.readLock().lock();
		try {
			return index.get(key);
		} finally {
			indexLock.readLock().unlock();
		}
	}

	protected synchronized void reindex() {
		indexLock.writeLock().lock();
		try {
			this.index = Maps.uniqueIndex(this.support, indexer);
		} finally {
			indexLock.writeLock().unlock();
		}
	}

	public void add(int index, T element) {
		support.add(index, element);
		reindex();
	}

	public boolean add(T e) {
		boolean ok = support.add(e);
		if (ok) {
			reindex();
		}
		return ok;
	}

	public boolean addAll(Collection<? extends T> c) {
		boolean ok = support.addAll(c);
		if (ok) {
			reindex();
		}
		return ok;
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		boolean ok = support.addAll(index, c);
		if (ok) {
			reindex();
		}
		return ok;
	}

	public void clear() {
		support.clear();
		reindex();
	}

	public Iterator<T> iterator() {
		// FIXME Re-indexation on iterator.removed
		return support.iterator();
	}

	public ListIterator<T> listIterator() {
		// FIXME Re-indexation on iterator.removed
		return support.listIterator();
	}

	public ListIterator<T> listIterator(int index) {
		// FIXME Re-indexation on iterator.removed
		return support.listIterator(index);
	}

	public T remove(int index) {
		T e = support.remove(index);
		reindex();
		return e;
	}

	public boolean remove(Object o) {
		boolean ok = support.remove(o);
		if (ok) {
			reindex();
		}
		return ok;
	}

	public boolean removeAll(Collection<?> c) {
		boolean ok = support.removeAll(c);
		if (ok) {
			reindex();
		}
		return ok;
	}

	public boolean retainAll(Collection<?> c) {
		boolean ok = support.retainAll(c);
		if (ok) {
			reindex();
		}
		return ok;
	}

	public T set(int index, T element) {
		T e = support.set(index, element);
		reindex();
		return e;
	}

}
