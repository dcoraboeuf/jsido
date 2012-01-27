package net.sf.sido.gen.model.support.java;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoType;

public class BasicRefPropertyBinder extends AbstractPropertyBinder<SidoRefProperty> {

	@Override
	public JClass getFieldSingleClass(GenerationContext generationContext, SidoRefProperty property) {
		return JClassUtils.createClassRef(generationContext, property.getType());
	}

	@Override
	public String getFieldSingleDefault(GenerationContext generationContext, SidoRefProperty property,
			JClass propertyClass) {
		SidoType type = property.getType();
		if (type.isAbstractType()) {
			// Cannot initialise an abstract type
			return null;
		} else {
			JClass typeClass = JClassUtils.createClassRef(generationContext, type);
			return String.format("new %s()", typeClass.getName());
		}
	}

}