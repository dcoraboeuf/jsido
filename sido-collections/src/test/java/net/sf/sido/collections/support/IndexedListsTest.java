package net.sf.sido.collections.support;

import static org.junit.Assert.assertSame;
import net.sf.sido.collections.IndexedList;

import org.junit.Test;

public class IndexedListsTest {
	
	@Test
	public void name() {
		IndexedList<User, Integer> users = IndexedLists.<User,Integer>indexedList("id");
		User user = new User(10, "Name");
		users.add(user);
		assertSame(user, users.getByIndex(10));
	}

}
