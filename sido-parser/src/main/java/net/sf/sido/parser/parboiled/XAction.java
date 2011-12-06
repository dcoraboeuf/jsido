package net.sf.sido.parser.parboiled;

import net.sf.sido.parser.model.XSchema;
import net.sf.sido.parser.model.XType;

import org.parboiled.Action;
import org.parboiled.Context;

public class XAction implements Action<Object> {

	private XSchema schema;
	private XType type;

	@Override
	public boolean run(Context<Object> context) {
		return false;
	}

	public XSchema getSchema() {
		return schema;
	}

	boolean schema(String uri) {
		schema = new XSchema(uri);
		return true;
	}

	boolean prefix(String uid, String prefix) {
		schema.prefix (prefix, uid);
		return true;
	}

	boolean type(String name) {
		type = schema.type(name);
		return true;
	}

}
