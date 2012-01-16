package net.sf.sido.gen.model.jfx;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.support.java.BasicSimplePropertyBinder;
import net.sf.sido.gen.model.support.java.JClass;
import net.sf.sido.schema.SidoSimpleProperty;

import org.apache.commons.lang3.ClassUtils;

public class JavaFXSimplePropertyBinder extends BasicSimplePropertyBinder implements JavaFXPropertyBinder<SidoSimpleProperty<?>> {
	
	private static final String JAVAFX_PROPERTY_PACKAGE = "javafx.beans.property";

	@Override
	public JClass getFieldJavaFXProperty(GenerationContext generationContext,
			SidoSimpleProperty<?> property) {
		Class<?> type = property.getType().getType();
		if (ClassUtils.isPrimitiveWrapper(type)) {
			return new JClass(JAVAFX_PROPERTY_PACKAGE, String.format(".%sProperty", type.getSimpleName()));
		} else if (String.class.isAssignableFrom(type)) {
			return new JClass(JAVAFX_PROPERTY_PACKAGE, "SimpleStringProperty");
		} else {
			return new JClass(JAVAFX_PROPERTY_PACKAGE, "SimpleObjectProperty")
				.addParameter(type);
		}
	}

}
