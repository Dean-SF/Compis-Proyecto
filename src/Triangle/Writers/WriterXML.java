package Triangle.Writers;

import Triangle.AbstractSyntaxTrees.Program;

import java.io.FileWriter;
import java.io.IOException;

public class WriterXML {

    private String fileName;

    public WriterXML(String fileName) {
        this.fileName = fileName.substring(0,fileName.length()-4) + ".xml";
    }

    // Draw the AST representing a complete program.
    public void writeXML(Program ast) throws IOException,NullPointerException {
        // Prepare the file to write
        FileWriter fileWriter = new FileWriter(fileName);

        //XML header
        fileWriter.write("<?xml version=\"1.0\" standalone=\"yes\"?>\n");

        WriterXMLVisitor layout = new WriterXMLVisitor(fileWriter);
        ast.visit(layout, null);

        fileWriter.close();
    }

}
