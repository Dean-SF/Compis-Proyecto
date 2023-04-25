package Triangle.Writers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriterHTML {
    
    private String fileName;

    String currentHTML;

    public WriterHTML(String fileName) {
        this.fileName = fileName.substring(0,fileName.length()-4) + ".html";
        currentHTML = "";
    }
    
    public void writeNewLine() {
        currentHTML += "<br>\n";
    }

    public void writeSpace() {
        currentHTML += " ";
    }

    public void writeTab() {
        currentHTML += " &emsp; ";
    }

    public void writeReturn() {
        currentHTML += "\r\n";
    }

    public void defaultWrite(String token) {
        currentHTML += token;
    }

    public void reservedWrite(String token) {
        currentHTML += "<strong>" + token + "</strong>";
    }

    public void literalWrite(String token) {
        currentHTML += "<font color='#0000cd'>" + token + "</font>";
    }

    public void commentedWrite(String token) {
        currentHTML += "<font color='#00b300'>" + token + "</font>";
        if(token.charAt(token.length()-1) == '\n')
            writeNewLine();
    }

    public void exportFile() throws IOException {
        currentHTML = "<p style=\"font-family: DejaVu Sans Mono;\">\n" + 
                      currentHTML + "\n"+
                      "</p>";
        
        try (BufferedWriter fileSaver = new BufferedWriter(new FileWriter(fileName))) {
            try {
				fileSaver.write(currentHTML);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

}
