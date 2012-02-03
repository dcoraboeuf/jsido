package net.sf.sido.collections.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class BasicIndexedListTest {
	
	@Test(expected = NullPointerException.class)
	public void constructor_null_list () {
		new BasicIndexedList<User, String>(null, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void constructor_list_no_indexer () {
		new BasicIndexedList<User, String>(Collections.<User>emptyList(), null);
	}
	
	@Test
	public void delegate () {
		List<User> support = Collections.<User>emptyList();
		BasicIndexedList<User, String> list = new BasicIndexedList<User, String>(support, User.nameFn);
		assertSame(support, list.delegate());
	}
	
	@Test
	public void getByIndex_none () {
		List<User> support = Collections.<User>emptyList();
		BasicIndexedList<User, String> list = new BasicIndexedList<User, String>(support, User.nameFn);
		assertNull(list.getByIndex("Albert"));
	}
	
	@Test
	public void getByIndex_not_found () {
		List<User> support = Collections.singletonList(new User (10, "Bernard"));
		BasicIndexedList<User, String> list = new BasicIndexedList<User, String>(support, User.nameFn);
		assertNull(list.getByIndex("Albert"));
	}
	
	@Test
	public void getByIndex_found () {
		User albert = new User(1, "Albert");
		List<User> support = Arrays.asList(albert, new User (10, "Bernard"));
		BasicIndexedList<User, String> list = new BasicIndexedList<User, String>(support, User.nameFn);
		assertSame(albert, list.getByIndex("Albert"));
	}
	
	@Test
	public void add() {
		BasicIndexedList<User, String> list = asIndexedList(new User(10, "Bernard"));
		assertNull(list.getByIndex("Albert"));
		User albert = new User(1, "Albert");
		list.add(albert);
		assertSame(albert, list.getByIndex("Albert"));
		assertEquals(1, list.indexOf(albert));
	}
	
	@Test
	public void add_index() {
		BasicIndexedList<User, String> list = asIndexedList(new User(10, "Bernard"));
		assertNull(list.getByIndex("Albert"));
		User albert = new User(1, "Albert");
		list.add(0, albert);
		assertSame(albert, list.getByIndex("Albert"));
		assertEquals(0, list.indexOf(albert));
	}
	
	@Test
	public void addAll() {
		BasicIndexedList<User, String> list = asIndexedList(new User(10, "Bernard"));
		assertNull(list.getByIndex("Albert"));
		assertNull(list.getByIndex("Charles"));
		User albert = new User(1, "Albert");
		User charles = new User(2, "Charles");
		list.addAll(Arrays.asList(albert, charles));
		assertSame(albert, list.getByIndex("Albert"));
		assertSame(charles, list.getByIndex("Charles"));
		assertEquals(1, list.indexOf(albert));
		assertEquals(2, list.indexOf(charles));
	}
	
	@Test
	public void addAll_index() {
		BasicIndexedList<User, String> list = asIndexedList(new User(10, "Bernard"));
		assertNull(list.getByIndex("Albert"));
		assertNull(list.getByIndex("Charles"));
		User albert = new User(1, "Albert");
		User charles = new User(2, "Charles");
		list.addAll(0, Arrays.asList(albert, charles));
		assertSame(albert, list.getByIndex("Albert"));
		assertSame(charles, list.getByIndex("Charles"));
		assertEquals(0, list.indexOf(albert));
		assertEquals(1, list.indexOf(charles));
	}
	
	@Test
	public void clear() {
		User albert = new User(1, "Albert");
		BasicIndexedList<User, String> list = asIndexedList(albert, new User(10, "Bernard"));
		assertSame(albert, list.getByIndex("Albert"));
		assertEquals(0, list.indexOf(albert));
		list.clear();
		assertTrue(list.isEmpty());
		assertNull(list.getByIndex("Albert"));
	}
	
	@Test
	public void removeIndex() {
		User albert = new User(1, "Albert");
		BasicIndexedList<User, String> list = asIndexedList(albert, new User(10, "Bernard"));
		assertSame(albert, list.getByIndex("Albert"));
		assertEquals(0, list.indexOf(albert));
		assertSame(albert, list.remove(0));
		assertEquals(1, list.size());
		assertNull(list.getByIndex("Albert"));
	}
	
	@Test
	public void removeObject() {
		User albert = new User(1, "Albert");
		BasicIndexedList<User, String> list = asIndexedList(albert, new User(10, "Bernard"));
		assertSame(albert, list.getByIndex("Albert"));
		assertEquals(0, list.indexOf(albert));
		assertTrue(list.remove(albert));
		assertEquals(1, list.size());
		assertNull(list.getByIndex("Albert"));
	}
	
	@Test
	public void removeAll() {
		User albert = new User(1, "Albert");
		BasicIndexedList<User, String> list = asIndexedList(albert, new User(10, "Bernard"));
		assertSame(albert, list.getByIndex("Albert"));
		assertEquals(0, list.indexOf(albert));
		assertTrue(list.removeAll(Arrays.asList(albert)));
		assertEquals(1, list.size());
		assertNull(list.getByIndex("Albert"));
	}
	
	@Test
	public void retainAll() {
		User albert = new User(1, "Albert");
		BasicIndexedList<User, String> list = asIndexedList(albert, new User(10, "Bernard"));
		assertSame(albert, list.getByIndex("Albert"));
		assertEquals(0, list.indexOf(albert));
		assertTrue(list.retainAll(Arrays.asList(albert)));
		assertEquals(1, list.size());
		assertSame(albert, list.getByIndex("Albert"));
	}
	
	@Test
	public void set() {
		User albert = new User(1, "Albert");
		BasicIndexedList<User, String> list = asIndexedList(albert, new User(10, "Bernard"));
		assertSame(albert, list.getByIndex("Albert"));
		assertEquals(0, list.indexOf(albert));
		User charles = new User(2, "Charles");
		assertSame(albert, list.set(0, charles));
		assertEquals(2, list.size());
		assertNull(list.getByIndex("Albert"));
		assertSame(charles, list.getByIndex("Charles"));
	}

	protected BasicIndexedList<User, String> asIndexedList(User... users) {
		List<User> support = asList(users);
		BasicIndexedList<User, String> list = new BasicIndexedList<User, String>(support, User.nameFn);
		return list;
	}

	protected List<User> asList(User... users) {
		return new ArrayList<User>(Arrays.asList(users));
	}

}
