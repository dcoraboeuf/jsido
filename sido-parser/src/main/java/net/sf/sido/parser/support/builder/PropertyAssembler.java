package net.sf.sido.parser.support.builder;

import net.sf.sido.parser.model.XProperty;
import net.sf.sido.schema.SidoProperty;

public interface PropertyAssembler {

	<P extends SidoProperty> P create(TypeDefinition definition, XProperty xProperty);

}
