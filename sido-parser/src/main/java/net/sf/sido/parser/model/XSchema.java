package net.sf.sido.parser.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XSchema {

	private final String uid;
	private final Map<String, String> prefixes = new HashMap<String, String>();
	private final List<XType> types = new ArrayList<XType>();

	public XSchema(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void prefix(String prefix, String uid) {
		prefixes.put(prefix, uid);
	}

	public XType type(String name) {
		XType type = new XType(name);
		types.add(type);
		return type;
	}

}
