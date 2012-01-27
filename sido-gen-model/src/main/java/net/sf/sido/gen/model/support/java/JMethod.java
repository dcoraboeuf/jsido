package net.sf.sido.gen.model.support.java;

import java.io.IOException;
import java.io.PrintWriter;

public class JMethod extends JAbstractMethod<JMethod> {

    private JClass returnType;
    private final String name;
    private boolean staticMethod;

    public JMethod(JClass parent, String name) {
        this(parent, name, new JClass("", "void"));
    }

    public JMethod(JClass parent, String name, JClass returnType) {
        super(parent);
        this.name = name;
        this.returnType = returnType;
    }
    
    public JMethod setReturnType(JClass returnType) {
		this.returnType = returnType;
		return this;
	}

    @Override
    protected void writeDecl(PrintWriter writer) throws IOException {
        writer.format("%s%s %s", staticMethod ? "static " : "", returnType.getReferenceName(), name);
        writeParams(writer);
    }

    public JClass getReturnType() {
        return returnType;
    }

    public String getName() {
        return name;
    }

    public JMethod setStatic() {
        staticMethod = true;
        return this;
    }

    public boolean isStaticMethod() {
        return staticMethod;
    }
}
