package net.sf.sido.gen.java;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class JAbstractMethod<T extends JAbstractMethod<T>> extends JMember<T> {

    private final Map<String, String> params = new LinkedHashMap<String,String>();
    private final List<String> content = new ArrayList<String>();

    protected JAbstractMethod(JClass parent) {
        super(parent);
    }

    public Map<String, String> getParams() {
        return params;
    }

    @SuppressWarnings("unchecked")
    public T addParam (String type, String name) {
        params.put(name, type);
        return (T) this;
    }

    public T addParam (JClass jClass, String name) {
        // FIXME Import
        // OK
        return addParam(jClass.getName(), name);
    }

    public List<String> getContent() {
        return content;
    }

    @SuppressWarnings("unchecked")
    public T addContent (String line, Object... params) {
        content.add(String.format(line, params));
        return (T) this;
    }

    protected void writeParams(PrintWriter writer) throws IOException {
        List<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            String name = param.getKey();
            String type = param.getValue();
            list.add(String.format("%s %s", type, name));
        }
        writer.format("(%s)", StringUtils.join(list, ", "));
    }

    @Override
    protected void writeContent(PrintWriter writer) throws IOException {
        // TODO Abstract method
        writer.format("{%n");
        for (String line : content) {
            writer.format("\t\t%s%n", line);
        }
        writer.format("\t}%n%n");
    }
}
