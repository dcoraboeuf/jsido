package net.sf.sido.parser.support.builder;

import net.sf.sido.parser.model.XProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoType;

public class RefPropertyAssembler extends AbstractPropertyAssembler {

	private final SidoType refType;

	public RefPropertyAssembler(SidoType refType) {
		this.refType = refType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SidoRefProperty create(TypeDefinition definition,
			XProperty xProperty) {
		return definition.getTypeBuilder().addProperty(xProperty.getName(),
				refType, xProperty.isNullable(), xProperty.isCollection(),
				xProperty.getIndex());
	}

}
