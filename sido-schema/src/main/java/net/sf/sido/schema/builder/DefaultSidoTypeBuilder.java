package net.sf.sido.schema.builder;

import org.apache.commons.lang3.Validate;

import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoSimpleType;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.model.DefaultSidoAnonymousProperty;
import net.sf.sido.schema.model.DefaultSidoRefProperty;
import net.sf.sido.schema.model.DefaultSidoSimpleProperty;
import net.sf.sido.schema.model.DefaultSidoType;

public class DefaultSidoTypeBuilder implements SidoTypeBuilder {

	private final SidoSchemaBuilder schemaBuilder;
	private final DefaultSidoType type;

	public DefaultSidoTypeBuilder(SidoSchemaBuilder schemaBuilder, String name) {
		Validate.notNull(schemaBuilder, "Schema builder is required");
		this.schemaBuilder = schemaBuilder;
		this.type = new DefaultSidoType(schemaBuilder.getSchema(), name);
	}

	@Override
	public SidoTypeBuilder setParentType(SidoType parentType) {
		type.setParentType(parentType);
		return this;
	}
	
	@Override
	public SidoTypeBuilder setAbstract() {
		type.setAbstractType(true);
		return this;
	}
	
	@Override
	public SidoType getType() {
		return type;
	}
	
	@Override
	public void close() {
		type.close();
		schemaBuilder.close(this);
	}

	@Override
	public SidoAnonymousProperty addAnonymousProperty(String name,
			boolean nullable, boolean collection) {
		DefaultSidoAnonymousProperty property = new DefaultSidoAnonymousProperty(name, nullable, collection);
		this.type.addProperty(property);
		return property;
	}

	@Override
	public <T> SidoSimpleProperty<T> addProperty(String name,
			SidoSimpleType<T> type, boolean nullable, boolean collection) {
		DefaultSidoSimpleProperty<T> property = new DefaultSidoSimpleProperty<T>(name, type, nullable, collection);
		this.type.addProperty(property);
		return property;
	}

	@Override
	public SidoRefProperty addProperty(String name, SidoType type,
			boolean nullable, boolean collection, String index) {
		DefaultSidoRefProperty property = new DefaultSidoRefProperty(name, type, nullable, collection, index);
		this.type.addProperty(property);
		return property;
	}

}
