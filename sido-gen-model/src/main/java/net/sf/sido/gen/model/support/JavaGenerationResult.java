package net.sf.sido.gen.model.support;

import java.util.HashSet;
import java.util.Set;

import net.sf.sido.gen.java.JClass;

public class JavaGenerationResult extends AbstractGenerationResult {
	
	private final Set<JClass> classes = new HashSet<JClass>();

	public void addClass(JClass j) {
		classes.add(j);
	}

}
