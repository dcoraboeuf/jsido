package net.sf.sido.gen.model.pojo;

import java.util.ArrayList;
import java.util.List;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.Options;
import net.sf.sido.gen.model.support.java.AbstractJavaGenerationModel;
import net.sf.sido.gen.model.support.java.AbstractPropertyBinder;
import net.sf.sido.gen.model.support.java.JClass;
import net.sf.sido.gen.model.support.java.JField;
import net.sf.sido.gen.model.support.java.JMethod;
import net.sf.sido.gen.model.support.java.PropertyBinder;
import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoType;

public class POJOGenerationModel extends AbstractJavaGenerationModel {

	public static final String NON_NULLABLE_COLLECTION_FINAL = "nonNullableCollectionFinal";
	public static final String COLLECTION_INTERFACE = "collectionInterface";
	public static final String COLLECTION_IMPLEMENTATION = "collectionImplementation";

	protected class SimplePropertyBinder extends AbstractPropertyBinder<SidoSimpleProperty<?>> {

		@Override
		public JClass getFieldSingleClass(GenerationContext generationContext, SidoSimpleProperty<?> property) {
			return new JClass(property.getType().getType());
		}

		@Override
		public String getFieldSingleDefault(GenerationContext generationContext, SidoSimpleProperty<?> property,
				JClass propertyClass) {
			return property.getType().getDefaultJavaInitialization();
		}

	}

	protected class AnonymousPropertyBinder extends AbstractPropertyBinder<SidoAnonymousProperty> {

		@Override
		public JClass getFieldSingleClass(GenerationContext generationContext, SidoAnonymousProperty property) {
			return new JClass(Object.class);
		}

		@Override
		public String getFieldSingleDefault(GenerationContext generationContext, SidoAnonymousProperty property,
				JClass propertyClass) {
			return null;
		}

	}

	protected class RefPropertyBinder extends AbstractPropertyBinder<SidoRefProperty> {

		@Override
		public JClass getFieldSingleClass(GenerationContext generationContext, SidoRefProperty property) {
			return createClassRef(generationContext, property.getType());
		}

		@Override
		public String getFieldSingleDefault(GenerationContext generationContext, SidoRefProperty property,
				JClass propertyClass) {
			SidoType type = property.getType();
			if (type.isAbstractType()) {
				// Cannot initialise an abstract type
				return null;
			} else {
				JClass typeClass = createClassRef(generationContext, type);
				return String.format("new %s()", typeClass.getName());
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
	protected String getGetMethodName(SidoProperty property) {
		if (property instanceof SidoSimpleProperty) {
			SidoSimpleProperty<?> simpleProperty = (SidoSimpleProperty<?>) property;
			if (Boolean.class.equals(simpleProperty.getType().getType()) && !simpleProperty.isNullable() && !simpleProperty.isCollection()) {
				return getPrefixedMethodName("is", property);
			}
		}
		// Default
		return super.getGetMethodName(property);
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

	/**
	 * Generates a {@link List} field.
	 */
	@Override
	protected void generateCollectionProperty(SidoProperty property, JClass c, GenerationContext generationContext,
			SidoType type) {
		// Options
		Options options = generationContext.getOptions();
		boolean optionNonNullableCollectionFinal = options.getBoolean(NON_NULLABLE_COLLECTION_FINAL, false);
		Class<?> optionCollectionInterface = options.getClass(COLLECTION_INTERFACE, List.class);
		Class<?> optionCollectionImplementation = options.getClass(COLLECTION_IMPLEMENTATION, ArrayList.class);
		// Field name
		String fieldName = getFieldName(property);
		// Field class
		JClass fieldClass = getFieldSingleClass(generationContext, property);
		c.addImport(fieldClass);
		// Collection type
		c.addImport(optionCollectionInterface);
		// Field declaration
		String fieldClassName = fieldClass.getName();
		String collectionTypeName = String.format("%s<%s>", optionCollectionInterface.getSimpleName(), fieldClassName);
		JField field = c.addField(collectionTypeName, fieldName);
		// Getter
		c.addMethod(getGetMethodName(property), collectionTypeName).addContent("return %s;", fieldName);
		// Adding a collection of elements
		JMethod m = c.addMethod(getAddMethodName(property)).addParam(String.format("%s...", fieldClassName), "pValues");
		if (property.isNullable()) {
			c.addImport(optionCollectionImplementation);
			m
					.addContent("if (%s == null) {", fieldName)
						.addContent("\t%s = new %s<%s>();", fieldName, optionCollectionImplementation.getSimpleName(), fieldClassName)
						.addContent("}");
		}
		m
				.addContent("for (%s pValue : pValues) {", fieldClassName)
					.addContent("\t%s.add(pValue);", fieldName)
					.addContent("}");
		// If not nullable, initializes it
		if (!property.isNullable()) {
			// Final field (optional)
			if (optionNonNullableCollectionFinal) {
				field.addModifier("final");
			}
			// Initialization
			c.addImport(optionCollectionImplementation);
			field.setInitialisation("new %s<%s>()", optionCollectionImplementation.getSimpleName(), fieldClassName);
		}
		// Setter (only for nullable collection - configurable)
		if (property.isNullable() || !optionNonNullableCollectionFinal) {
			c
					.addMethod(getSetMethodName(property))
						.addParam(collectionTypeName, "pValues")
						.addContent("%s = pValues;", fieldName);
		}
	}
}
