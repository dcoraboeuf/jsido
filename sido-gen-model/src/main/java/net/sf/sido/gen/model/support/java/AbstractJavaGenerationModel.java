package net.sf.sido.gen.model.support.java;

import java.util.Collection;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.GenerationListener;
import net.sf.sido.gen.model.support.AbstractGenerationModel;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoType;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractJavaGenerationModel extends AbstractGenerationModel<JavaGenerationResult> {

	public AbstractJavaGenerationModel(String id) {
		super(id);
	}

	@Override
	public JavaGenerationResult createResultInstance() {
		return new JavaGenerationResult();
	}

	@Override
	public void generate(JavaGenerationResult result, SidoType type, GenerationContext generationContext,
			GenerationListener listener) {
		JClass j = generateClass(result, type, generationContext, listener);
		result.addClass(j);
	}

	protected JClass generateClass(JavaGenerationResult result, SidoType type, GenerationContext generationContext,
			GenerationListener listener) {
		// Class
		JClass c = createClass(generationContext, type);
		// Properties
		generateProperties(c, generationContext, type);
		// OK
		return c;
	}

	protected JClass createClass(GenerationContext generationContext, SidoType type) {
		JClass c = createClassRef(generationContext, type);
		// Abstraction?
		if (type.isAbstractType()) {
			c.setAbstractClass(true);
		}
		// Subclass?
		SidoType parentType = type.getParentType();
		if (parentType != null) {
			String parentTypeName = getSimpleClassName(generationContext, parentType);
			c.setParent(parentTypeName);
			c.addImport(getPackage(generationContext, parentType));
		}
		// Constructors
		generateConstructors(c, generationContext, type);
		// OK
		return c;
	}

	protected JClass createClassRef(GenerationContext generationContext, SidoType type) {
		return new JClass(getPackage(generationContext, type), getSimpleClassName(generationContext, type));
	}

	/**
	 * Does not generate any constructor by default
	 */
	protected void generateConstructors(JClass c, GenerationContext generationContext, SidoType type) {
	}

	/**
	 * Generation of members for all properties
	 */
	protected void generateProperties(JClass c, GenerationContext generationContext, SidoType type) {
		Collection<SidoProperty> properties = type.getProperties();
		for (SidoProperty property : properties) {
			generateProperty(property, c, generationContext, type);
		}
	}

	/**
	 * Generation of a property
	 */
	protected void generateProperty(SidoProperty property, JClass c, GenerationContext generationContext, SidoType type) {
		if (property.isCollection()) {
			// FIXME Collection property
		} else {
			generateSingleProperty(property, c, generationContext, type);
		}
	}

	protected void generateSingleProperty(SidoProperty property, JClass c, GenerationContext generationContext,
			SidoType type) {
		// Field name
		String fieldName = getFieldName(property);
		// Field class
		JClass fieldClass = getFieldSingleClass(generationContext, property);
		// Field declaration
		JField field = c.addField(fieldClass, fieldName);
		// TODO If not nullable, initializes it
		// if (!property.isNullable()) {
		// field.setInitialisation(getFieldSingleDefault(generationContext,
		// property));
		// }
		// Getter
		c.addMethod(getGetMethodName(property), fieldClass).addContent("return %s;", fieldName);
		// Setter
		c.addMethod(getSetMethodName(property)).addParam(fieldClass, "pValue").addContent("%s = pValue;", fieldName);
	}

	protected <T extends SidoProperty> JClass getFieldSingleClass(GenerationContext generationContext, T property) {
		PropertyBinder<T> binder = getPropertyBinder(property);
		if (binder == null) {
			throw new IllegalStateException(String.format("Cannot find any property binder for %s", property));
		}
		return binder.getFieldSingleClass(generationContext, property);
	}

	protected abstract <T extends SidoProperty> PropertyBinder<T> getPropertyBinder(T property);

	protected String getFieldName(SidoProperty property) {
		return property.getName();
	}

	protected String getSimpleClassName(GenerationContext generationContext, SidoType type) {
		return StringUtils.capitalize(type.getName());
	}

	protected String getPackage(GenerationContext generationContext, SidoType type) {
		return type.getSchema().getUid();
	}

}
