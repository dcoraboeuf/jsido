package net.sf.sido.gen.model.support.java;

import java.util.Collection;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.GenerationListener;
import net.sf.sido.gen.model.support.AbstractGenerationModel;
import net.sf.sido.schema.SidoAnonymousProperty;
import net.sf.sido.schema.SidoProperty;
import net.sf.sido.schema.SidoRefProperty;
import net.sf.sido.schema.SidoSimpleProperty;
import net.sf.sido.schema.SidoType;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractJavaGenerationModel extends AbstractGenerationModel<JavaGenerationResult> {

	public static final String CHAINED_SETTER = "chainedSetter";
	public static final String NO_PRIMITIVE_TYPE = "noPrimitiveType";

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
			c.addImport(createClassRef(generationContext, parentType));
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
			if (StringUtils.isNotBlank(property.getCollectionIndex())) {
				// FIXME generateIndexedCollectionProperty(property, c, generationContext, type);
			} else {
				generateCollectionProperty(property, c, generationContext, type);
			}
		} else {
			generateSingleProperty(property, c, generationContext, type);
		}
	}

	protected abstract void generateCollectionProperty(SidoProperty property, JClass c, GenerationContext generationContext,
			SidoType type);

	protected abstract void generateSingleProperty(SidoProperty property, JClass c, GenerationContext generationContext,
			SidoType type);

	protected <T extends SidoProperty> String getFieldSingleDefault(GenerationContext generationContext, T property, JClass propertyClass) {
		PropertyBinder<T> binder = loadPropertyBinder(property);
		return binder.getFieldSingleDefault(generationContext, property, propertyClass); 
	}

	protected <T extends SidoProperty> JClass getFieldSingleClass(GenerationContext generationContext, T property) {
		PropertyBinder<T> binder = loadPropertyBinder(property);
		return binder.getFieldSingleClass(generationContext, property);
	}

	protected <T extends SidoProperty> JClass getFieldCollectionClass(GenerationContext generationContext, T property) {
		PropertyBinder<T> binder = loadPropertyBinder(property);
		return binder.getFieldCollectionClass(generationContext, property);
	}

	protected <T extends SidoProperty> PropertyBinder<T> loadPropertyBinder(T property) {
		PropertyBinder<T> binder = getPropertyBinder(property);
		if (binder == null) {
			throw new IllegalStateException(String.format("Cannot find any property binder for %s", property));
		} else {
			return binder;
		}
	}
	
	@SuppressWarnings("unchecked")
	protected <P extends PropertyBinder<T>, T extends SidoProperty> P getPropertyBinder(T property) {
		if (property instanceof SidoSimpleProperty) {
			return (P) getSimplePropertyBinder((SidoSimpleProperty<?>) property);
		} else if (property instanceof SidoAnonymousProperty) {
			return (P) getAnonymousPropertyBinder((SidoAnonymousProperty) property);
		} else if (property instanceof SidoRefProperty) {
			return (P) getRefPropertyBinder((SidoRefProperty) property);
		} else {
			return null;
		}
	}

	protected abstract PropertyBinder<? extends SidoSimpleProperty<?>> getSimplePropertyBinder(SidoSimpleProperty<?> property);
	protected abstract PropertyBinder<? extends SidoAnonymousProperty> getAnonymousPropertyBinder(SidoAnonymousProperty property);
	protected abstract PropertyBinder<? extends SidoRefProperty> getRefPropertyBinder(SidoRefProperty property);

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
