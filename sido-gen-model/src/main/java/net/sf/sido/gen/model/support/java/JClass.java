package net.sf.sido.gen.model.support.java;

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
    
    private final List<String> parameters = new ArrayList<String>();

    private final List<JField> fields = new ArrayList<JField>();
    private final List<JConstructor> constructors = new ArrayList<JConstructor>();
    private final List<JMethod> methods = new ArrayList<JMethod>();
    
    public JClass (Class<?> type) {
    	this (type.isPrimitive() ? null : type.getPackage().getName(), type.getSimpleName());
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

    protected void addImport(String s) {
        importNames.add(s);
    }
    
    public JClass addParameter (Class<?> type) {
    	return addParameter(new JClass(type));
    }

    public JClass addParameter(JClass type) {
    	addImport(type);
    	return addParameter(type.getName());
	}

    protected JClass addParameter(String expression) {
    	parameters.add(expression);
    	return this;
	}

	public JField addField(String type, String name) {
		return addField(new JClass("", type), name);
    }

    public JField addField(JClass type, String name) {
        addImport(type);
        JField field = new JField(this, type, name);
        fields.add(field);
        return field;
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
    	return addMethod(name, new JClass("", returnType));
    }

    public JMethod addMethod(String name, JClass returnType) {
        addImport(returnType);
        JMethod method = new JMethod(this, name, returnType);
        methods.add(method);
        return method;
    }

    public void write(PrintWriter writer) throws IOException {
    	// Checks
    	if (StringUtils.isBlank(packageName)) {
    		throw new IllegalStateException("Cannot write a class with no package");
    	}
        // TODO Header
        // Package
        writer.format("package %s;%n%n", packageName);
        // Imports
        writeClassImports(writer);
        // Class doc
        writeDoc(writer, 0);
        // Class body
        writeClassBody(writer);
    }

	protected void writeClassImports(PrintWriter writer) {
		if (!importNames.isEmpty()) {
	        for (String importName : importNames) {
	            writer.format("import %s;%n", importName);
	        }
	        writer.println();
        }
	}

	protected void writeClassBody(PrintWriter writer) throws IOException {
		// Class declaration
        writeClassDeclaration(writer);
        // Fields
        writeClassFields(writer);
        // Constructors
        writeClassConstructors(writer);
        // Methods
        writeClassMethods(writer);
        // End of class
        writer.format("}%n");
	}

	protected void writeClassMethods(PrintWriter writer) throws IOException {
		for (JMethod method : methods) {
            method.write(writer);
        }
	}

	protected void writeClassConstructors(PrintWriter writer)
			throws IOException {
		for (JConstructor constructor : constructors) {
            constructor.write(writer);
        }
	}

	protected void writeClassFields(PrintWriter writer) throws IOException {
		if (!fields.isEmpty()) {
	        for (JField field : fields) {
	            field.write(writer);
	        }
	        writer.format("%n");
        }
	}

	protected void writeClassDeclaration(PrintWriter writer) {
		// Class declaration
        writer.print("public");
        // Modifiers
        if (abstractClass) {
            writer.print(" abstract");
        }
        // Class name
        writer.format(" class %s", name);
        // Parameters
        if (!parameters.isEmpty()) {
        	writer.format("<%s>", parameterListAsString());
        }
        // Separator
        writer.format(" ");
        // Parent
        if (StringUtils.isNotBlank(parent)) {
            writer.format("extends %s ", parent);
        }
        // Start of class
        writer.format("{%n%n");
	}

	protected String parameterListAsString() {
		return StringUtils.join(parameters, ",");
	}

    public void addImport(JClass cls) {
        String otherPackageName = cls.getPackageName();
        if (StringUtils.isNotBlank(otherPackageName) && !packageName.equals(otherPackageName) && !"java.lang".equals(otherPackageName)) {
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

	public String getReferenceName() {
		if (parameters.isEmpty()) {
			return getName();
		} else {
			return String.format("%s<%s>", getName(), parameterListAsString());
		}
	}
	
}
