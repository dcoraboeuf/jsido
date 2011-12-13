package net.sf.sido.parser.support.builder;

import net.sf.sido.parser.model.XProperty;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoSimpleType;

public class SimplePropertyAssembler<T> extends AbstractPropertyAssembler {

	private final SidoSimpleType<T> simpleType;

	public SimplePropertyAssembler(SidoSimpleType<T> simpleType) {
		this.simpleType = simpleType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SidoSimpleProperty<T> create(TypeDefinition definition,
			XProperty xProperty) {
		return definition.getTypeBuilder().addProperty(definition.getName(),
				simpleType, xProperty.isNullable(), xProperty.isCollection());
	}

}
