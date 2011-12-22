package net.sf.sido.gen.model.support.java;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;

public class JField extends JMember<JField> {

    private final String type;
    private final String name;
    
    private String initialisation; 

    public JField(JClass parent, String type, String name) {
        super(parent);
        setScope("private");
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getInitialisation() {
		return initialisation;
	}

	public JField setInitialisation(String pattern, Object... parameters) {
		this.initialisation = String.format(pattern, parameters);
		return this;
	}

	@Override
    protected void writeDecl(PrintWriter writer) throws IOException {
        writer.format("%s %s", type, name);
        if (StringUtils.isNotBlank(initialisation)) {
        	writer.format(" = %s", initialisation);
        }
        writer.format(";%n", type, name);
    }

    /**
     * Nothing to write
     */
    @Override
    protected void writeContent(PrintWriter writer) throws IOException {
    }
}
