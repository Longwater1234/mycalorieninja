package org.davistiba;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintFile {
    private String fileContent, fileName;

    public void setfileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void makeFile() throws IOException {
        if (fileName.isBlank() || fileContent.isBlank()) {
            throw new IOException("Must set both filename and fileContent");
        } else {
            String pattern = "[^0-9a-zA-Z._]";
            fileName = fileName.replaceAll(pattern, "").toLowerCase().trim();
            fileContent = fileContent.trim();
            // REMEMBER add regex filter for fileName as shown above
            File file = new File("E:\\JAVA_wkspc\\fileoutputs\\" + fileName);
            if (file.exists())
                throw new IOException("File already exists");
            // else..
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(fileContent);
            bufferedWriter.newLine();
            bufferedWriter.append("File created: ").append(SimpleDateFormat.getDateTimeInstance().format(new Date()));
            bufferedWriter.close();
            System.out.println("File has been successfully created");

        }
    }
}
