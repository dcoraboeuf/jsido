package net.sf.sido.schema.model;

import static net.sf.sido.property.support.PropertyFactory.mutable;

import java.util.Collection;
import java.util.Collections;

import net.sf.sido.property.Property;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;

public class DefaultSidoType extends AbstractSidoItem implements SidoType {

	private final String name;
	private final Property<SidoSchema> schema = mutable("schema");

	public DefaultSidoType(String name) {
		this.name = name;
	}

	@Override
	public Collection<? extends Property<?>> getProperties() {
		return Collections.singleton(schema);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public SidoSchema getSchema() {
		return schema.get();
	}

	public void setSchema(SidoSchema schema) {
		this.schema.set(schema);
	}

}
