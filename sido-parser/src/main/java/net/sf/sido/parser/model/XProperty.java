package net.sf.sido.parser.model;

public class XProperty {

	private String name;
	private boolean nullable;
	private boolean array;
	private String index;
	private XPropertyTypeRef propertyTypeRef;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isArray() {
		return array;
	}

	public void setArray(boolean array) {
		this.array = array;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public XPropertyTypeRef getPropertyTypeRef() {
		return propertyTypeRef;
	}

	public void setPropertyTypeRef(XPropertyTypeRef propertyTypeRef) {
		this.propertyTypeRef = propertyTypeRef;
	}

}
