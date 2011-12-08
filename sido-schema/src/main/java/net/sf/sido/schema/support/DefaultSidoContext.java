package net.sf.sido.schema.support;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.model.DefaultSidoSchema;

import org.apache.commons.lang3.Validate;

public class DefaultSidoContext implements SidoContext {
	
	private final Map<String, SidoSchema> schemas = new ConcurrentHashMap<String, SidoSchema>();
	
	@Override
	public Collection<SidoSchema> getSchemas() {
		return schemas.values();
	}
	
	@Override
	public SidoSchema getSchema(String name, boolean required) {
		SidoSchema schema = schemas.get(name);
		if (schema == null && required) {
			throw new SidoSchemaNotFoundException(name);
		} else {
			return schema;
		}
	}
	
	@Override
	public SidoSchema createSchema(String uid) {
		SidoSchema schema = new DefaultSidoSchema(this, uid);
		registerSchema(schema);
		return schema;
	}
	
	@Override
	public synchronized void registerSchema(SidoSchema schema) {
		Validate.notNull(schema, "The schema is required");
		String uid = schema.getUid();
		Validate.notBlank(uid, "The schema UID is required");
		if (schemas.containsKey(uid)) {
			throw new SidoSchemaUIDDuplicationException(uid);
		} else {
			schemas.put(uid, schema);
		}
	}

}
