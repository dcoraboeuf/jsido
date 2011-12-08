package net.sf.sido.parser.parboiled;

import net.sf.sido.parser.model.XSchema;
import net.sf.sido.parser.model.XType;
import net.sf.sido.parser.model.XTypeRef;
import net.sf.sido.parser.support.SidoMoreThanOneParentException;

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

	boolean trace(String message, String context) {
		System.out.format("-- %s -- %s --%n", message, context);
		return true;
	}

	boolean abstractType() {
		checkType();
		type.setAbstractType(true);
		return true;
	}

	boolean parent(String typeRef) {
		checkType();
		XTypeRef parent = type.getParent();
		if (parent != null) {
			throw new SidoMoreThanOneParentException(type.getName());
		} else {
			type.setParent(new XTypeRef(typeRef));
			return true;
		}
	}

	private void checkType() {
		if (type == null) {
			throw new IllegalStateException("No type has been defined yet.");
		}
	}

}
