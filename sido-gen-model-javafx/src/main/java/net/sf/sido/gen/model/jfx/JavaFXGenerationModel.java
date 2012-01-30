package net.sf.sido.gen.model.jfx;

import java.util.Arrays;

import javafx.collections.ObservableList;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.support.java.AbstractJavaGenerationModel;
import net.sf.sido.gen.model.support.java.JClass;
import net.sf.sido.gen.model.support.java.JClassUtils;
import net.sf.sido.gen.model.support.java.JField;
import net.sf.sido.gen.model.support.java.JMethod;
import net.sf.sido.gen.model.support.java.PropertyBinder;
import net.sf.sido.javafx.ObservableIndexedList;
import net.sf.sido.javafx.SidoJavaFXFactory;
import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoType;

import org.apache.commons.lang3.StringUtils;

public class JavaFXGenerationModel extends AbstractJavaGenerationModel {

	private final JavaFXSimplePropertyBinder simplePropertyBinder = new JavaFXSimplePropertyBinder();
	private final JavaFXAnonymousPropertyBinder anonymousPropertyBinder = new JavaFXAnonymousPropertyBinder();
	private final JavaFXRefPropertyBinder refPropertyBinder = new JavaFXRefPropertyBinder();

	public JavaFXGenerationModel() {
		super("javafx");
	}
	
	@Override
	protected void generateIndexedCollectionProperty(SidoRefProperty property,
			JClass c, GenerationContext generationContext, SidoType type) {		
		// Field name
		String fieldName = getFieldName(property);
		// Field class
		SidoType fieldSidoType = property.getType();
		JClass fieldClass = JClassUtils.createClassRef(generationContext, fieldSidoType);
		// Index class
		String indexName = property.getCollectionIndex();
		SidoProperty indexProperty = fieldSidoType.getProperty(indexName, true);
		JClass indexClass = getFieldCollectionClass(generationContext, indexProperty);
		JClass indexSimpleClass = getFieldSingleClass(generationContext, indexProperty);
		
		// Collection type
		JClass collectionType = new JClass(ObservableIndexedList.class);
		// ... element type
		collectionType.addParameter(fieldClass);
		// ... index type
		collectionType.addParameter(indexClass);
		
		// Field declaration
		JField field = c.addField(collectionType, fieldName);
		// If not nullable, initializes it
		if (!property.isNullable()) {
			field.addModifier("final");
			// Initialization
			c.addImport(SidoJavaFXFactory.class);
			field.setInitialisation("SidoJavaFXFactory.observableIndexedList(\"%s\")", indexName);
		}
		
		// Getter
		c.addMethod(getGetMethodName(property), collectionType).addContent("return %s;", fieldName);
		// GetByIndex
		c.addMethod(getGetByIndexMethodName(property), fieldClass)
			.addParam(indexSimpleClass, "pKey")
			.addContent("return %s.getByIndex(pKey);", fieldName);
		// Adding a collection of elements
		JMethod m = c.addMethod(getAddMethodName(property)).addParam(String.format("%s...", fieldClass.getReferenceName()), "pValues");
		if (property.isNullable()) {
			c.addImport(SidoJavaFXFactory.class);
			m
					.addContent("if (%s == null) {", fieldName)
					.addContent("\t%s = SidoJavaFXFactory.observableIndexedList(\"%s\");", fieldName, indexName)
					.addContent("}");
		}
		c.addImport(Arrays.class);
		m.addContent("%s.addAll(Arrays.asList(pValues));", fieldName);
		// Setter (only for nullable collection - configurable)
		if (property.isNullable()) {
			c
					.addMethod(getSetMethodName(property))
						.addParam(collectionType, "pValues")
						.addContent("%s = pValues;", fieldName);
		}
	}

	@Override
	protected void generateCollectionProperty(SidoProperty property, JClass c,
			GenerationContext generationContext, SidoType type) {
		// Options
		// Field name
		String fieldName = getFieldName(property);
		// Field class
		JClass fieldClass = getFieldCollectionClass(generationContext, property);
		c.addImport(fieldClass);
		// Collection type
		JClass collectionType = new JClass(ObservableList.class).addParameter(fieldClass);
		c.addImport(collectionType);
		c.addImport(new JClass("javafx.collections", "FXCollections"));
		// Field declaration
		JField field = c.addField(collectionType, fieldName);
		// If not nullable, initializes it
		if (!property.isNullable()) {
			field.addModifier("final");
			// Initialization
			field.setInitialisation("FXCollections.observableArrayList()");
		}
		// Getter
		c.addMethod(getGetMethodName(property), collectionType).addContent("return %s;", fieldName);
		// Adding a collection of elements
		JMethod m = c.addMethod(getAddMethodName(property)).addParam(String.format("%s...", fieldClass.getReferenceName()), "pValues");
		if (property.isNullable()) {
			m
					.addContent("if (%s == null) {", fieldName)
						.addContent("\t%s = FXCollections.observableArrayList();", fieldName)
						.addContent("}");
		}
		m.addContent("%s.addAll (pValues);", fieldName);
		// Setter (only for nullable collection - configurable)
		if (property.isNullable()) {
			c
					.addMethod(getSetMethodName(property))
						.addParam(collectionType, "pValues")
						.addContent("%s = pValues;", fieldName);
		}
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
			} else {
				field.setInitialisation(String.format("new %s(this, \"%s\")", propertyClass.getReferenceName(), fieldName));
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
			setMethod.setReturnType(c);
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
		return refPropertyBinder;
	}

}
