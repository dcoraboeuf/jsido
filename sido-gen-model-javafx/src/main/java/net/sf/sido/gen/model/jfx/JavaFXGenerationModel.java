package net.sf.sido.gen.model.jfx;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.support.java.AbstractJavaGenerationModel;
import net.sf.sido.gen.model.support.java.JClass;
import net.sf.sido.gen.model.support.java.JMethod;
import net.sf.sido.gen.model.support.java.PropertyBinder;
import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoType;

public class JavaFXGenerationModel extends AbstractJavaGenerationModel {

	private final JavaFXSimplePropertyBinder simplePropertyBinder = new JavaFXSimplePropertyBinder();

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
		// Field name
		String fieldName = getFieldName(property);
		// Field class
		JClass fieldClass = getFieldSingleClass(generationContext, property);
		// Property type
		JClass propertyClass = getFieldPropertyClass(generationContext, property);
		// TODO Field declaration using the property type
		// JField field = c.addField(fieldClass, fieldName);
		// TODO If not nullable, provides a default value
		// TODO Property field initialisation
		// if (!property.isNullable()) {
		// // Initialization
		// String initialization = getFieldSingleDefault(generationContext,
		// property, fieldClass);
		// if (StringUtils.isNotBlank(initialization)) {
		// field.setInitialisation(initialization);
		// }
		// }
		//
		// Property getter
		// TODO Returns the property type
		c.addMethod(String.format("%sProperty", fieldName));
		// Getter
		c.addMethod(getGetMethodName(property), fieldClass)
				.addModifier("final").addContent("return %s.get();", fieldName);
		// Setter
		JMethod setMethod = c.addMethod(getSetMethodName(property))
				.addParam(fieldClass, "pValue")
				.addContent("%s.set(pValue);", fieldName);
		if (generationContext.getOptions().getBoolean(CHAINED_SETTER, false)) {
			setMethod.setReturnType(c.getName());
			setMethod.addContent("return this;");
		}
	}

	protected <T extends SidoProperty> JClass getFieldPropertyClass(GenerationContext generationContext,
			T property) {
		JavaFXPropertyBinder<T> binder = getPropertyBinder(property);
		return binder.getFieldJavaFXProperty(generationContext, property);
	}

	@Override
	protected PropertyBinder<? extends SidoSimpleProperty<?>> getSimplePropertyBinder(
			SidoSimpleProperty<?> property) {
		return simplePropertyBinder;
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
