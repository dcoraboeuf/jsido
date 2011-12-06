package net.sf.sido.schema.support;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;

public class DefaultSidoContext implements SidoContext {
	
	private final Map<String, SidoSchema> schemas = new HashMap<String, SidoSchema>();
	
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

}
