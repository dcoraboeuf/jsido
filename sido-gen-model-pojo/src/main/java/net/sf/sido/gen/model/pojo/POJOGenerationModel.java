package net.sf.sido.gen.model.pojo;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.support.java.AbstractJavaGenerationModel;
import net.sf.sido.gen.model.support.java.AbstractPropertyBinder;
import net.sf.sido.gen.model.support.java.JClass;
import net.sf.sido.gen.model.support.java.PropertyBinder;
import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoType;

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
	
	protected class RefPropertyBinder extends AbstractPropertyBinder<SidoRefProperty> {

		@Override
		public JClass getFieldSingleClass(GenerationContext generationContext,
				SidoRefProperty property) {
			return createClassRef(generationContext, property.getType());
		}

		@Override
		public String getFieldSingleDefault(
				GenerationContext generationContext, SidoRefProperty property,
				JClass propertyClass) {
			SidoType type = property.getType();
			if (type.isAbstractType()) {
				// Cannot initialise an abstract type
				return null;
			} else {
				JClass typeClass = createClassRef(generationContext, type);
				return String.format ("new %s()", typeClass.getName());
			}
		}
		
	}

	private final SimplePropertyBinder simplePropertyBinder = new SimplePropertyBinder();
	private final AnonymousPropertyBinder anonymousPropertyBinder = new AnonymousPropertyBinder();
	private final RefPropertyBinder refPropertyBinder = new RefPropertyBinder();
	
	public POJOGenerationModel() {
		super("pojo");
	}

	@Override
	protected PropertyBinder<? extends SidoSimpleProperty<?>> getSimplePropertyBinder(SidoSimpleProperty<?> property) {
		return simplePropertyBinder;
	}

	@Override
	protected PropertyBinder<? extends SidoAnonymousProperty> getAnonymousPropertyBinder(SidoAnonymousProperty property) {
		return anonymousPropertyBinder;
	}

	@Override
	protected PropertyBinder<? extends SidoRefProperty> getRefPropertyBinder(SidoRefProperty property) {
		return refPropertyBinder;
	}
	
	

}
