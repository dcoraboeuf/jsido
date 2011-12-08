package net.sf.sido.parser.support.builder;

import net.sf.sido.parser.model.XType;
import net.sf.sido.schema.model.DefaultSidoSchema;
import net.sf.sido.schema.model.DefaultSidoType;

public class TypeDefinition {

	private final DefaultSidoSchema schema;
	private final XType sType;

	private ResolutionStatus status;
	private DefaultSidoType type;

	public TypeDefinition(DefaultSidoSchema schema, XType sType) {
		this.schema = schema;
		this.sType = sType;
	}

}
