package net.sf.sido.gen.model.support.java;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.schema.SidoAnonymousProperty;

public class BasicAnonymousPropertyBinder extends AbstractPropertyBinder<SidoAnonymousProperty> {

	@Override
	public JClass getFieldSingleClass(GenerationContext generationContext, SidoAnonymousProperty property) {
		return new JClass(Object.class);
	}

	@Override
	public String getFieldSingleDefault(GenerationContext generationContext, SidoAnonymousProperty property,
			JClass propertyClass) {
		return null;
	}

}