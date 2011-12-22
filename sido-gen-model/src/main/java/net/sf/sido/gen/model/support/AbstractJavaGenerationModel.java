package net.sf.sido.gen.model.support;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import net.sf.sido.gen.java.JClass;
import net.sf.sido.gen.java.JField;
import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.GenerationListener;
import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoType;

public abstract class AbstractJavaGenerationModel extends AbstractGenerationModel<JavaGenerationResult> {

	public AbstractJavaGenerationModel(String id) {
		super(id);
	}
	
	@Override
	public JavaGenerationResult createResultInstance() {
		return new JavaGenerationResult();
	}
	
	@Override
	public void generate(JavaGenerationResult result, SidoType type,
			GenerationContext generationContext, GenerationListener listener) {
		JClass j = generateClass (result, type, generationContext, listener);
		result.addClass(j);
	}

	protected JClass generateClass(JavaGenerationResult result, SidoType type,
			GenerationContext generationContext, GenerationListener listener) {
		// Class
		JClass c = createClass(generationContext, type);
		// Properties
		generateProperties(c, generationContext, type);
		// OK
		return c;
	}

	protected JClass createClass(GenerationContext generationContext,
			SidoType type) {
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
	protected void generateConstructors(
			JClass c,
			GenerationContext generationContext,
			SidoType type) {
	}
	
	/**
	 * Generation of members for all properties
	 */
	protected void generateProperties(
			JClass c,
			GenerationContext generationContext,
			SidoType type) {
		Collection<SidoProperty> properties = type.getProperties();
		for (SidoProperty property : properties) {
			generateProperty (property, c, generationContext, type);
		}
	}

	/**
	 * Generation of a property
	 */
	protected void generateProperty(
			SidoProperty property,
			JClass c,
			GenerationContext generationContext,
			SidoType type) {
		if (property.isCollection()) {
			// FIXME Collection property
		} else {
			generateSingleProperty(property, c, generationContext, type);
		}
	}

	protected void generateSingleProperty(
			SidoProperty property,
			JClass c,
			GenerationContext generationContext,
			SidoType type) {
		// Field name
		String fieldName = getFieldName(property);
		// Field class
		JClass fieldClass = getFieldSingleClass(generationContext, property);
		// Field declaration
		JField field = c.addField(fieldClass, fieldName);
		// TODO If not nullable, initializes it
		// TODO Getter
		// TODO Setter
	}

	protected JClass getFieldSingleClass(GenerationContext generationContext, SidoProperty property) {
		if (property instanceof SidoSimpleProperty) {
			return getFieldSingleSimpleClass(generationContext, (SidoSimpleProperty<?>) property);
		} else if (property instanceof SidoRefProperty) {
			return getFieldSingleRefClass(generationContext, (SidoRefProperty) property);
		} else if (property instanceof SidoAnonymousProperty) {
			return getFieldSingleAnonymousClass(generationContext, (SidoAnonymousProperty) property);
		} else {
			throw new IllegalStateException("Unknown property class: " + property.getClass());
		}
	}

	protected abstract JClass getFieldSingleAnonymousClass(GenerationContext generationContext, SidoAnonymousProperty property);

	protected JClass getFieldSingleRefClass(GenerationContext generationContext, SidoRefProperty property) {
		return createClassRef(generationContext, property.getType());
	}

	protected JClass getFieldSingleSimpleClass(GenerationContext generationContext, SidoSimpleProperty<?> property) {
		return new JClass(property.getType().getType());
	}

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
