package net.sf.sido.gen.model.support.java;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.schema.SidoProperty;

public interface PropertyBinder<T extends SidoProperty> {

	JClass getFieldSingleClass(GenerationContext generationContext, T property);

	JClass getFieldCollectionClass(GenerationContext generationContext, T property);

	String getFieldSingleDefault(GenerationContext generationContext, T property, JClass propertyClass);

}
