package net.sf.sido.gen.model.support.java;

import java.io.IOException;
import java.io.PrintWriter;

public class JMethod extends JAbstractMethod<JMethod> {

    private final String returnType;
    private final String name;
    private boolean staticMethod;

    public JMethod(JClass parent, String name) {
        this(parent, name, "void");
    }

    public JMethod(JClass parent, String name, String returnType) {
        super(parent);
        this.name = name;
        this.returnType = returnType;
    }

    @Override
    protected void writeDecl(PrintWriter writer) throws IOException {
        writer.format("%s%s %s", staticMethod ? "static " : "", returnType, name);
        writeParams(writer);
    }

    public String getReturnType() {
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
