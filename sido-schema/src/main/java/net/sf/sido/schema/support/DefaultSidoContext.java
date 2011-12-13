package net.sf.sido.schema.support;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoSimpleType;
import net.sf.sido.schema.SidoType;

import org.apache.commons.lang3.StringUtils;
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
	
	@Override
	public SidoType getType(String uri, String name, boolean required) {
		SidoSchema schema = getSchema(uri, required);
		if (schema != null) {
			return schema.getType(name, required);
		} else {
			return null;
		}
	}

	@Override
	public SidoType getType(String qualifiedName, boolean required) {
		// Gets the schema name and the type name
		int pos = StringUtils.indexOf(qualifiedName, SCHEMA_SEPARATOR);
		if (pos < 0) {
			throw new SidoWrongQualifiedNameException(qualifiedName);
		}
		String schemaName = StringUtils.substringBefore(qualifiedName, SCHEMA_SEPARATOR);
		String typeName = StringUtils.substringAfter(qualifiedName, SCHEMA_SEPARATOR);
		return getType (schemaName, typeName, required);
	}

	@Override
	public <T> SidoSimpleType<T> getSimpleType(String name, boolean required) {
		// TODO Auto-generated method stub
		return null;
	}

}
