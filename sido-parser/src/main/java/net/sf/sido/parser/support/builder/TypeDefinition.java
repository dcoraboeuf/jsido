package net.sf.sido.parser.support.builder;

import net.sf.sido.parser.model.XType;
import net.sf.sido.schema.SidoSchema;
import net.sf.sido.schema.model.DefaultSidoType;

public class TypeDefinition {

	private final SidoSchema schema;
	private final XType xType;

	private ResolutionStatus status;
	private DefaultSidoType type;

	public TypeDefinition(SidoSchema schema, XType xType) {
		this.schema = schema;
		this.xType = xType;
	}
	
	public String getName() {
		return xType.getName();
	}
	
	public String getQualifiedName() {
		return String.format("%s::%s", schema.getUid(), getName());
	}

}
