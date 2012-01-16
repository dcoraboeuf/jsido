package net.sf.sido.gen.model.jfx;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.support.java.AbstractJavaGenerationModel;
import net.sf.sido.gen.model.support.java.JClass;
import net.sf.sido.gen.model.support.java.PropertyBinder;
import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoType;

public class JavaFXGenerationModel extends AbstractJavaGenerationModel {

	public JavaFXGenerationModel() {
		super("javafx");
	}

	@Override
	protected void generateCollectionProperty(SidoProperty property, JClass c,
			GenerationContext generationContext, SidoType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void generateSingleProperty(SidoProperty property, JClass c,
			GenerationContext generationContext, SidoType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected PropertyBinder<? extends SidoSimpleProperty<?>> getSimplePropertyBinder(
			SidoSimpleProperty<?> property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PropertyBinder<? extends SidoAnonymousProperty> getAnonymousPropertyBinder(
			SidoAnonymousProperty property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PropertyBinder<? extends SidoRefProperty> getRefPropertyBinder(
			SidoRefProperty property) {
		// TODO Auto-generated method stub
		return null;
	}

}
