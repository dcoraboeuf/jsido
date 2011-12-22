package net.sf.sido.gen.model.support.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public abstract class JItem<T extends JItem<T>> {
    
    protected final List<String> doc = new ArrayList<String>(0);

    private String info;

    @SuppressWarnings("unchecked")
	public T addDoc(String line, Object... params) {
        doc.add(String.format(line, params));
        return (T) this;
    }

    protected void writeDoc (PrintWriter writer, int indentLevel) throws IOException {
        String indent = StringUtils.repeat("\t", indentLevel);
        if (!doc.isEmpty()) {
            writer.format("%s/**%n", indent);
            for (String line : doc) {
                writer.format("%s * %s%n", indent, line);
            }
            writer.format("%s */%n", indent);
        }
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
