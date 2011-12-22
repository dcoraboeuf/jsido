package net.sf.sido.gen.model.support;

import org.apache.commons.lang3.StringUtils;

import net.sf.sido.gen.model.GenerationModel;
import net.sf.sido.gen.model.GenerationResult;
import net.sf.sido.schema.SidoProperty;

public abstract class AbstractGenerationModel<R extends GenerationResult> implements GenerationModel<R> {

	private final String id;

	public AbstractGenerationModel(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	protected String getGetMethodName(SidoProperty property) {
		return getPrefixedMethodName("get", property);
	}
	
	protected String getSetMethodName(SidoProperty property) {
		return getPrefixedMethodName("set", property);
	}

	protected String getPrefixedMethodName(String prefix, SidoProperty property) {
		String name = StringUtils.capitalize(property.getName());
		return prefix + name;
	}

}
