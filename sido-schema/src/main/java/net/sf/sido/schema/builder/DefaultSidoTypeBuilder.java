package net.sf.sido.schema.builder;

import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoSimpleType;
import net.sf.sido.schema.SidoType;
import net.sf.sido.schema.model.DefaultSidoType;

public class DefaultSidoTypeBuilder implements SidoTypeBuilder {

	private final SidoSchemaBuilder schemaBuilder;
	private final DefaultSidoType type;

	public DefaultSidoTypeBuilder(SidoSchemaBuilder schemaBuilder, String name) {
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
			boolean nullable, boolean collection, String index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> SidoSimpleProperty<T> addProperty(String name,
			SidoSimpleType<T> type, boolean nullable, boolean collection,
			String index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SidoRefProperty addProperty(String name, SidoType type,
			boolean nullable, boolean collection, String index) {
		// TODO Auto-generated method stub
		return null;
	}

}
