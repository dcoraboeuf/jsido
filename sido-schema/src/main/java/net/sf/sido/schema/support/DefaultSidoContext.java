package net.sf.sido.schema.support;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.sido.schema.SidoContext;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoSimpleType;
import net.sf.sido.schema.SidoType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSidoContext implements SidoContext {
	
	private static final Logger logger = LoggerFactory.getLogger(SidoContext.class);
	
	public static Collection<? extends SidoSimpleType<?>> getDefaultSimpleTypes() {
		List<SidoSimpleType<?>> list = new ArrayList<SidoSimpleType<?>>();
		list.add(SimpleType.get("string", String.class, "\"\""));
		list.add(SimpleType.get("boolean", Boolean.class, "false"));
		list.add(SimpleType.get("byte", Byte.class, "0"));
		list.add(SimpleType.get("short", Short.class, "0"));
		list.add(SimpleType.get("integer", Integer.class, "0"));
		list.add(SimpleType.get("long", Long.class, "0L"));
		list.add(SimpleType.get("float", Float.class, "0f"));
		list.add(SimpleType.get("double", Double.class, "0d"));
		list.add(SimpleType.get("decimal", BigDecimal.class, "BigDecimal.ZERO"));
		// Detection of simple types
		@SuppressWarnings("rawtypes")
		Iterator<SidoSimpleType> extensions = ServiceLoader.load(SidoSimpleType.class).iterator();
		while (extensions.hasNext()) {
			@SuppressWarnings("rawtypes")
			SidoSimpleType type = (SidoSimpleType) extensions.next();
			list.add(type);
			logger.debug("[context] Loading simple type for {}", type.getName());
		}
		// OK
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
