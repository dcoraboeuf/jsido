package net.sf.sido.gen.model.support.java;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.schema.SidoSimpleProperty;

import org.apache.commons.lang3.ClassUtils;

public class BasicSimplePropertyBinder extends AbstractPropertyBinder<SidoSimpleProperty<?>> {

	@Override
	public JClass getFieldSingleClass(GenerationContext generationContext, SidoSimpleProperty<?> property) {
		Class<?> type = property.getType().getType();
		if (property.isNullable() || generationContext.getOptions().getBoolean(AbstractJavaGenerationModel.NO_PRIMITIVE_TYPE, false)) {
			return new JClass(type);
		} else {
			// Gets the associated primitive type, if any
			Class<?> primitiveType = ClassUtils.wrapperToPrimitive(type);
			if (primitiveType != null) {
				return new JClass(primitiveType);
			} else {
				return new JClass(type);
			}
		}
	}
	
	@Override
	public JClass getFieldCollectionClass(GenerationContext generationContext, SidoSimpleProperty<?> property) {
		return new JClass(property.getType().getType());
	}

	@Override
	public String getFieldSingleDefault(GenerationContext generationContext, SidoSimpleProperty<?> property,
			JClass propertyClass) {
		return property.getType().getDefaultJavaInitialization();
	}

}