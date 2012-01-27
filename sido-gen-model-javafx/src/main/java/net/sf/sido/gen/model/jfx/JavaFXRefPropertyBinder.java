package net.sf.sido.gen.model.jfx;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.support.java.BasicRefPropertyBinder;
import net.sf.sido.gen.model.support.java.JClass;
import net.sf.sido.gen.model.support.java.JClassUtils;
import net.sf.sido.schema.SidoRefProperty;

public class JavaFXRefPropertyBinder extends BasicRefPropertyBinder implements JavaFXPropertyBinder<SidoRefProperty> {

	@Override
	public JClass getFieldJavaFXProperty(GenerationContext generationContext,
			SidoRefProperty property) {
		JClass ref = JClassUtils.createClassRef(generationContext, property.getType());
		return new JClass(JAVAFX_PROPERTY_PACKAGE, "SimpleObjectProperty").addParameter(ref);		
	}

}
