package net.sf.sido.schema.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.support.SidoTypeNotFoundInSchemaException;

public class DefaultSidoSchema extends AbstractSidoItem implements SidoSchema {

	private final SidoContext context;
	private final String uid;
	
	private Map<String, SidoType> types = new HashMap<String, SidoType>();

	public DefaultSidoSchema(SidoContext context, String uid) {
		Validate.notNull(context, "Context is required");
		Validate.notBlank(uid, "Schema UID is required");
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

	public void addType(SidoType type) {
		checkNotClosed();
		types.put(type.getName(), type);
	}

}
