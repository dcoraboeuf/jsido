package net.sf.sido.parser.model;

public class XPropertyTypeRef {

	private final XTypeRef typeRef;

	public XPropertyTypeRef() {
		this.typeRef = null;
	}

	public XPropertyTypeRef(String typeRef) {
		this.typeRef = new XTypeRef(typeRef);
	}

	public XTypeRef getTypeRef() {
		return typeRef;
	}

	public boolean isAnonymous() {
		return typeRef == null;
	}

}
