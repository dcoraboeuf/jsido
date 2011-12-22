package net.sf.sido.gen.model.pojo;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.support.java.AbstractJavaGenerationModel;
import net.sf.sido.gen.model.support.java.AbstractPropertyBinder;
import net.sf.sido.gen.model.support.java.JClass;
import net.sf.sido.gen.model.support.java.PropertyBinder;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoSimpleProperty;

public class POJOGenerationModel extends AbstractJavaGenerationModel {
	
	protected class SimplePropertyBinder extends AbstractPropertyBinder<SidoSimpleProperty<?>> {

		@Override
		public JClass getFieldSingleClass(GenerationContext generationContext, SidoSimpleProperty<?> property) {
			return new JClass(property.getType().getType());
		}
		
	}
	
	private final SimplePropertyBinder simplePropertyBinder = new SimplePropertyBinder();
	
	public POJOGenerationModel() {
		super("pojo");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected PropertyBinder<? extends SidoProperty> getPropertyBinder(SidoProperty property) {
		if (property instanceof SidoSimpleProperty) {
			return simplePropertyBinder;
		} else {
			return null;
		}
	}

}
