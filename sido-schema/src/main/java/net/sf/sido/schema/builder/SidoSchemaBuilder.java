package net.sf.sido.schema.builder;

import net.sf.sido.schema.SidoSchema;

public interface SidoSchemaBuilder {

	SidoSchema getSchema();

	void close();

	String getUid();

	SidoTypeBuilder newType(String name);

	void close(SidoTypeBuilder sidoTypeBuilder);

}
