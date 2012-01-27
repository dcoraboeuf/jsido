package net.sf.sido.gen.model.jfx;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.support.java.BasicAnonymousPropertyBinder;
import net.sf.sido.gen.model.support.java.JClass;
import net.sf.sido.schema.SidoAnonymousProperty;

public class JavaFXAnonymousPropertyBinder extends BasicAnonymousPropertyBinder implements JavaFXPropertyBinder<SidoAnonymousProperty> {

	@Override
	public JClass getFieldJavaFXProperty(GenerationContext generationContext,
			SidoAnonymousProperty property) {
		return new JClass(JAVAFX_PROPERTY_PACKAGE, "SimpleObjectProperty").addParameter(Object.class);
	}

}
