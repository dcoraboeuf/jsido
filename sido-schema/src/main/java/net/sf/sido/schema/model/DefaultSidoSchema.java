package net.sf.sido.schema.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.support.SidoTypeNotFoundInSchemaException;

public class DefaultSidoSchema extends AbstractSidoItem implements SidoSchema {

	private final SidoContext context;
	private final String uid;
	
	private Map<String, String> prefixes = new HashMap<String, String>();
	private Map<String, SidoType> types = new HashMap<String, SidoType>();

	public DefaultSidoSchema(SidoContext context, String uid) {
		this.context = context;
		this.uid = uid;
	}

	@Override
	public SidoContext getContext() {
		return context;
	}

	@Override
	public String getUid() {
		return uid;
	}
	
	@Override
	public String toString() {
		return uid;
	}

	@Override
	public SidoSchema getSchemaForPrefix(String prefix) {
		String ruid = prefixes.get(prefix);
		return context.getSchema(ruid, true);
	}

	@Override
	public Collection<SidoType> getTypes() {
		return types.values();
	}

	@Override
	public SidoType getType(String name, boolean required) {
		SidoType type = types.get(name);
		if (type == null && required) {
			throw new SidoTypeNotFoundInSchemaException (uid, name);
		} else {
			return type;
		}
	}

}
