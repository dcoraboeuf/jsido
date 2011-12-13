package net.sf.sido.schema.builder;

import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoSimpleType;
import net.sf.sido.schema.SidoType;

public interface SidoTypeBuilder {

	SidoTypeBuilder setParentType(SidoType parentType);

	SidoType getType();

	void close();

	SidoTypeBuilder setAbstract();

	SidoAnonymousProperty addAnonymousProperty(String name, boolean nullable, boolean collection);

	<T> SidoSimpleProperty<T> addProperty(String name, SidoSimpleType<T> type, boolean nullable, boolean collection);

	SidoRefProperty addProperty(String name, SidoType type, boolean nullable, boolean collection, String index);

}
