package net.sf.sido.gen.model.jfx;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.support.java.AbstractJavaGenerationModel;
import net.sf.sido.gen.model.support.java.JClass;
import net.sf.sido.gen.model.support.java.JField;
import net.sf.sido.gen.model.support.java.JMethod;
import net.sf.sido.gen.model.support.java.PropertyBinder;
import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoType;

import org.apache.commons.lang3.StringUtils;

public class JavaFXGenerationModel extends AbstractJavaGenerationModel {

	private final JavaFXSimplePropertyBinder simplePropertyBinder = new JavaFXSimplePropertyBinder();
	private final JavaFXAnonymousPropertyBinder anonymousPropertyBinder = new JavaFXAnonymousPropertyBinder();

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
		JClass propertyClass = getFieldPropertyClass(generationContext,
				property);
		// Field declaration using the property type
		JField field = c.addField(propertyClass, fieldName).addModifier("final");
		// If not nullable, provides a default value
		// Property field initialisation
		if (!property.isNullable()) {
			// Initialization
			String initialization = getFieldSingleDefault(generationContext,
					property, fieldClass);
			if (StringUtils.isNotBlank(initialization)) {
				field.setInitialisation(String.format("new %s(this, \"%s\", %s)", propertyClass.getReferenceName(), fieldName, initialization));
			}
		} else {
			field.setInitialisation(String.format("new %s(this, \"%s\")", propertyClass.getReferenceName(), fieldName));
		}

		// Property getter
		c.addMethod(String.format("%sProperty", fieldName), propertyClass)
				.addModifier("final").addContent("return %s;", fieldName);
		// Getter
		c.addMethod(getGetMethodName(property), fieldClass)
				.addModifier("final").addContent("return %s.get();", fieldName);
		// Setter
		JMethod setMethod = c.addMethod(getSetMethodName(property))
				.addModifier("final").addParam(fieldClass, "pValue")
				.addContent("%s.set(pValue);", fieldName);
		if (generationContext.getOptions().getBoolean(CHAINED_SETTER, false)) {
			setMethod.setReturnType(c.getName());
			setMethod.addContent("return this;");
		}
	}

	protected <T extends SidoProperty> JClass getFieldPropertyClass(
			GenerationContext generationContext, T property) {
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
		return anonymousPropertyBinder;
	}

	@Override
	protected PropertyBinder<? extends SidoRefProperty> getRefPropertyBinder(
			SidoRefProperty property) {
		// TODO Auto-generated method stub
		return null;
	}

}
