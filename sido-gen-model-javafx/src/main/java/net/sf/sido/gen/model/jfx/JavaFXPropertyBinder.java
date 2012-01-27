package net.sf.sido.gen.model.jfx;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.support.java.JClass;
import net.sf.sido.gen.model.support.java.PropertyBinder;
import net.sf.sido.schema.SidoProperty;

public interface JavaFXPropertyBinder<T extends SidoProperty> extends PropertyBinder<T> {
	
	String JAVAFX_PROPERTY_PACKAGE = "javafx.beans.property";
	
	JClass getFieldJavaFXProperty(GenerationContext generationContext, T property);

}
