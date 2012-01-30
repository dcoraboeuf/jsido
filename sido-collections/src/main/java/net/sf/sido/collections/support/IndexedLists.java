package net.sf.sido.collections.support;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.sf.sido.collections.IndexedList;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.common.base.Function;

public class IndexedLists {

	public static class PropertyFunction<T, K> implements Function<T, K> {

		private final String name;

		public PropertyFunction(String name) {
			this.name = name;
		}

		@SuppressWarnings("unchecked")
		@Override
		public K apply(T input) {
			try {
				return (K) PropertyUtils.getProperty(input, name);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(String.format("Cannot get property %s on %s", name, input), e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(String.format("Cannot get property %s on %s", name, input), e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(String.format("Cannot get property %s on %s", name, input), e);
			}
		}

	}
	
	public static <T, K> Function<T, K> propertyFunction (String name) {
		return new PropertyFunction<T, K>(name);
	}

	public static <T, K> IndexedList<T, K> indexedList(List<T> support,
			Function<T, K> indexer) {
		return new BasicIndexedList<T, K>(support, indexer);
	}

	public static <T, K> IndexedList<T, K> indexedList(Function<T, K> indexer) {
		return indexedList(new ArrayList<T>(), indexer);
	}

	public static <T, K> IndexedList<T, K> indexedList (String name) {
		return indexedList(new PropertyFunction<T,K>(name));
	}
}
