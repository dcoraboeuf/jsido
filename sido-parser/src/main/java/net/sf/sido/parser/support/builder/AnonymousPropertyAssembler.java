package net.sf.sido.parser.support.builder;

import net.sf.sido.parser.model.XProperty;
import net.sf.sido.schema.SidoAnonymousProperty;

public class AnonymousPropertyAssembler extends AbstractPropertyAssembler {

	@SuppressWarnings("unchecked")
	@Override
	public SidoAnonymousProperty create(TypeDefinition definition, XProperty xProperty) {
		return definition.getTypeBuilder().addAnonymousProperty(definition.getName(), xProperty.isNullable(), xProperty.isCollection());
	}

}
