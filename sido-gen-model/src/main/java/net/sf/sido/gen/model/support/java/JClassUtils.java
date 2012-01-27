package net.sf.sido.gen.model.support.java;

import org.apache.commons.lang3.StringUtils;

import net.sf.sido.gen.model.GenerationContext;
import net.sf.sido.schema.SidoType;

public class JClassUtils {

	public static JClass createClassRef(GenerationContext generationContext, SidoType type) {
		return new JClass(getPackage(generationContext, type), getSimpleClassName(generationContext, type));
	}

	public static String getPackage(GenerationContext generationContext, SidoType type) {
		return type.getSchema().getUid();
	}

	public static String getSimpleClassName(GenerationContext generationContext, SidoType type) {
		return StringUtils.capitalize(type.getName());
	}

}
