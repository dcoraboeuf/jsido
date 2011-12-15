package net.sf.sido.schema.support;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoSimpleType;
import net.sf.sido.schema.SidoType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class DefaultSidoContext implements SidoContext {
	
	public static Collection<? extends SidoSimpleType<?>> getDefaultSimpleTypes() {
		List<SidoSimpleType<?>> list = new ArrayList<SidoSimpleType<?>>();
		list.add(SimpleType.get("string", String.class));
		list.add(SimpleType.get("boolean", Boolean.class));
		list.add(SimpleType.get("byte", Byte.class));
		list.add(SimpleType.get("short", Short.class));
		list.add(SimpleType.get("integer", Integer.class));
		list.add(SimpleType.get("long", Long.class));
		list.add(SimpleType.get("float", Float.class));
		list.add(SimpleType.get("double", Double.class));
		list.add(SimpleType.get("decimal", BigDecimal.class));
		// TODO #7 Detection of simple types
		return list;
	}
	
	private final Map<String, SidoSchema> schemas = new ConcurrentHashMap<String, SidoSchema>();
	
	private final Map<String, SidoSimpleType<?>> simpleTypes = new ConcurrentHashMap<String, SidoSimpleType<?>>();
	
	/**
	 * Registers the default simple types
	 */
	public DefaultSidoContext() {
		this(getDefaultSimpleTypes());
	}

	/**
	 * Registration of simple types
	 */
	public DefaultSidoContext (Collection<? extends SidoSimpleType<?>> types) {
		for (SidoSimpleType<?> type : types) {
			simpleTypes.put(type.getName(), type);
		}
	}
	
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
		@SuppressWarnings("unchecked")
		SidoSimpleType<T> type = (SidoSimpleType<T>) simpleTypes.get(name);
		if (type == null && required) {
			throw new SidoSimpleTypeNotFoundException(name);
		} else {
			return type;
		}
	}

}
