package net.sf.sido.gen.java;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class JClass extends JItem<JClass> {

    private final String packageName;
    private final String name;

    private final Set<String> importNames = new TreeSet<String>();

    private String parent;
    private boolean abstractClass;

    private final List<JField> fields = new ArrayList<JField>();
    private final List<JConstructor> constructors = new ArrayList<JConstructor>();
    private final List<JMethod> methods = new ArrayList<JMethod>();
    
    public JClass (Class<?> type) {
    	this (type.getPackage().getName(), type.getSimpleName());
    }

    public JClass(String packageName, String name) {
        this.packageName = packageName;
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public Set<String> getImportNames() {
        return importNames;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isAbstractClass() {
        return abstractClass;
    }

    public void setAbstractClass(boolean abstractClass) {
        this.abstractClass = abstractClass;
    }

    public void addImport(String s) {
        // FIXME Checks for imported current package
        importNames.add(s);
    }

    public JField addField(String type, String name) {
        JField field = new JField(this, type, name);
        fields.add(field);
        return field;
    }

    public JField addField(JClass type, String name) {
        addImport(type);
        return addField(type.getName(), name);
    }

    public JConstructor addConstructor() {
        JConstructor jConstructor = new JConstructor(this);
        constructors.add(jConstructor);
        return jConstructor;
    }

    public JMethod addMethod(String name) {
        return addMethod(name, "void");
    }

    public JMethod addMethod(String name, String returnType) {
        JMethod method = new JMethod(this, name, returnType);
        methods.add(method);
        return method;
    }

    public void write(PrintWriter writer) throws IOException {
        // TODO Header
        // Package
        writer.format("package %s;%n%n", packageName);
        // Imports
        for (String importName : importNames) {
            writer.format("import %s;%n", importName);
        }
        // Class doc
        writeDoc(writer, 0);
        // Class declaration
        writer.print("public");
        // Modifiers
        if (abstractClass) {
            writer.print(" abstract");
        }
        // Class name
        writer.format(" class %s ", name);
        // Parent
        if (StringUtils.isNotBlank(parent)) {
            writer.format("extends %s ", parent);
        }
        // Start of class
        writer.format("{%n");
        // Fields
        for (JField field : fields) {
            field.write(writer);
        }
        // Constructors
        for (JConstructor constructor : constructors) {
            constructor.write(writer);
        }
        // Methods
        for (JMethod method : methods) {
            method.write(writer);
        }
        // End of class
        writer.format("}%n");
    }

    public JMethod addMethod(String methodName, JClass returnClass) {
        // Import the class
        addImport(returnClass);
        // OK
        return addMethod(methodName, returnClass.getName());
    }

    public void addImport(JClass cls) {
        String otherPackageName = cls.getPackageName();
        if (!packageName.equals(otherPackageName) && !"java.lang".equals(otherPackageName)) {
        	addImport(otherPackageName + "." + cls.getName());
        }
    }

    public void addImport(Class<?> cls) {
        String otherPackageName = cls.getPackage().getName();
        if (!packageName.equals(otherPackageName) && !"java.lang".equals(otherPackageName)) {
            addImport(cls.getName());
        }
    }

    public List<JConstructor> getConstructors() {
        return constructors;
    }

    public List<JMethod> getMethods() {
        return methods;
    }

    public String getQualifiedName() {
        return packageName + "." + name;
    }
    
    @Override
    public String toString() {
    	return name;
    }
}
