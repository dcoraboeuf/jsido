package net.sf.sido.parser.model;

import java.util.ArrayList;
import java.util.List;

public class XType {

	private final String name;
	private final List<XProperty> properties = new ArrayList<XProperty>();

	private boolean abstractType;
	private XTypeRef parent;

	public XType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isAbstractType() {
		return abstractType;
	}

	public void setAbstractType(boolean abstractType) {
		this.abstractType = abstractType;
	}

	public XTypeRef getParent() {
		return parent;
	}

	public void setParent(XTypeRef parent) {
		this.parent = parent;
	}
	
	

}
