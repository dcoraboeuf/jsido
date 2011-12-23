package net.sf.sido.gen.model.pojo;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.support.java.AbstractJavaGenerationModel;
import net.sf.sido.gen.model.support.java.AbstractPropertyBinder;
import net.sf.sido.gen.model.support.java.JClass;
import net.sf.sido.gen.model.support.java.PropertyBinder;
import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoSimpleProperty;

public class POJOGenerationModel extends AbstractJavaGenerationModel {
	
	protected class SimplePropertyBinder extends AbstractPropertyBinder<SidoSimpleProperty<?>> {

		@Override
		public JClass getFieldSingleClass(GenerationContext generationContext, SidoSimpleProperty<?> property) {
			return new JClass(property.getType().getType());
		}

		@Override
		public String getFieldSingleDefault(GenerationContext generationContext, SidoSimpleProperty<?> property,
				JClass propertyClass) {
			Object value = property.getType().getDefaultValue();
			return valueToString(value);
		}
		
	}
	
	protected class AnonymousPropertyBinder extends AbstractPropertyBinder<SidoAnonymousProperty> {

		@Override
		public JClass getFieldSingleClass(GenerationContext generationContext,
				SidoAnonymousProperty property) {
			return new JClass(Object.class);
		}

		@Override
		public String getFieldSingleDefault(
				GenerationContext generationContext,
				SidoAnonymousProperty property, JClass propertyClass) {
			return null;
		}
		
	}

	private final SimplePropertyBinder simplePropertyBinder = new SimplePropertyBinder();
	private final AnonymousPropertyBinder anonymousPropertyBinder = new AnonymousPropertyBinder();
	
	public POJOGenerationModel() {
		super("pojo");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected PropertyBinder<? extends SidoProperty> getPropertyBinder(SidoProperty property) {
		if (property instanceof SidoSimpleProperty) {
			return simplePropertyBinder;
		} else if (property instanceof SidoAnonymousProperty) {
			return anonymousPropertyBinder;
		} else {
			return null;
		}
	}

}
