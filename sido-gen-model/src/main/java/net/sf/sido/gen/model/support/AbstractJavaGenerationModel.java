package net.sf.sido.gen.model.support;

import org.apache.commons.lang3.StringUtils;

import net.sf.sido.gen.java.JClass;
import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.GenerationListener;
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
		// FIXME generateProperties(generationContext, c, type);
		// OK
		return c;
	}

	protected JClass createClass(GenerationContext generationContext,
			SidoType type) {
		JClass c = new JClass(getPackage(generationContext, type), getSimpleClassName(generationContext, type));
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
		// FIXME generateConstructors(c, type);
		// OK
		return c;
	}

	protected String getSimpleClassName(GenerationContext generationContext, SidoType type) {
		return StringUtils.capitalize(type.getName());
	}

	protected String getPackage(GenerationContext generationContext, SidoType type) {
		return type.getSchema().getUid();
	}

}
