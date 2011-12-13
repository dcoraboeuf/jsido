package net.sf.sido.schema.model;

import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.SidoType;

public class DefaultSidoType extends AbstractSidoItem implements SidoType {

	private final SidoSchema schema;
	private final String name;
	
	private SidoType parentType;

	public DefaultSidoType(SidoSchema schema, String name) {
		this.schema = schema;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public SidoSchema getSchema() {
		return schema;
	}
	
	@Override
	public SidoType getParentType() {
		return parentType;
	}

	public void setParentType(SidoType parentType) {
		this.parentType = parentType;
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

}
