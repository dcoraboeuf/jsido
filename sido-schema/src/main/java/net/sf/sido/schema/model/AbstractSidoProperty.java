package net.sf.sido.schema.model;

import net.sf.sido.schema.SidoProperty;

public abstract class AbstractSidoProperty implements SidoProperty {
	
	private final String name;
	private final boolean nullable;
	private final boolean collection;
	private final String collectionIndex;
	
	protected AbstractSidoProperty(String name) {
		this(name, false, false, null);
	}
	
	protected AbstractSidoProperty(String name, boolean nullable) {
		this(name, nullable, false, null);
	}
	
	protected AbstractSidoProperty(String name, boolean nullable, boolean collection) {
		this(name, nullable, collection, null);
	}
	
	protected AbstractSidoProperty(String name, boolean nullable, boolean collection, String collectionIndex) {
		this.name = name;
		this.nullable = nullable;
		this.collection = collection;
		this.collectionIndex = collectionIndex;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isNullable() {
		return nullable;
	}

	@Override
	public boolean isCollection() {
		return collection;
	}

	@Override
	public String getCollectionIndex() {
		return collectionIndex;
	}
	
	
}
