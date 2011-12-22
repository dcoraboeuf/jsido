package net.sf.sido.gen.model.pojo;

import net.sf.sido.gen.java.JClass;
import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.gen.model.support.AbstractJavaGenerationModel;
import net.sf.sido.schema.SidoAnonymousProperty;

public class POJOGenerationModel extends AbstractJavaGenerationModel {
	
	public POJOGenerationModel() {
		super("pojo");
	}
	
	@Override
	protected JClass getFieldSingleAnonymousClass(GenerationContext generationContext, SidoAnonymousProperty property) {
		return new JClass(Object.class);
	}

}
