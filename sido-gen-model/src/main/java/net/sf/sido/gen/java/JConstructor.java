package net.sf.sido.gen.java;

import java.io.IOException;
import java.io.PrintWriter;

public class JConstructor extends JAbstractMethod<JConstructor> {

    public JConstructor(JClass parent) {
        super(parent);
    }

    @Override
    protected void writeDecl(PrintWriter writer) throws IOException {
        writer.print(getParent().getName());
        writeParams(writer);
    }
}
