package net.sf.sido.collections.support;

import com.google.common.base.Function;

public class User {
	
	public static final Function<User, String> nameFn = new Function<User, String>() {
		@Override
		public String apply(User input) {
			return input.getName();
		}
	};

	private final int id;
	private final String name;

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
