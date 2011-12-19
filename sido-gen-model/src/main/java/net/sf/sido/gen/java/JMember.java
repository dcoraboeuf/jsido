package net.sf.sido.gen.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public abstract class JMember<T extends JMember<T>> extends JItem<T> {

    private final JClass parent;

    private String scope = "public";
    private final List<String> modifiers = new ArrayList<String>(0);
    private final List<String> annotations = new ArrayList<String>(0);

    protected JMember(JClass parent) {
        this.parent = parent;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public JClass getParent() {
        return parent;
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    @SuppressWarnings("unchecked")
	public T addModifier(String modifier) {
        modifiers.add(modifier);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T addAnnotation(String annotation) {
        annotations.add(annotation);
        return (T) this;
    }

    public String getScope() {
        return scope;
    }

    @SuppressWarnings("unchecked")
    public T setScope(String scope) {
        this.scope = scope;
        return (T) this;
    }

    public void write(PrintWriter writer) throws IOException {
        // Doc
        writeDoc(writer, 1);
        // Annotation
        for (String annotation : annotations) {
            writer.format("\t%s%n", annotation);
        }
        // Scope
        writer.format("\t%s ", scope);
        // Pre-defined modifiers
        writeModifiers(writer);
        // Modifiers
        if (!modifiers.isEmpty()) {
            writer.print(StringUtils.join(modifiers, " "));
        }
        // Declaration
        writeDecl(writer);
        // Content
        writeContent(writer);
    }

    protected void writeModifiers(PrintWriter writer) throws IOException {
    }

    protected abstract void writeDecl(PrintWriter writer) throws IOException;

    protected abstract void writeContent(PrintWriter writer) throws IOException;

}
