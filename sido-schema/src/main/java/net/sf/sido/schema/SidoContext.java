package net.sf.sido.schema;

import java.util.Collection;

public interface SidoContext {
	
	String SCHEMA_SEPARATOR = "::";
	
	Collection<SidoSchema> getSchemas();
	
	SidoSchema getSchema (String uid, boolean required);
	
	void registerSchema (SidoSchema schema);

	SidoType getType(String qualifiedName, boolean required);

	SidoType getType(String uri, String name, boolean required);

	<T> SidoSimpleType<T> getSimpleType(String name, boolean required);

}
