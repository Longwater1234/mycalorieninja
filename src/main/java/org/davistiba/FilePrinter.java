package org.davistiba;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FilePrinter {
    private String fileContent, fileName;

    public void setFileContent(String fileContent) {
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
            File file = new File("E:\\JAVA_wkspc\\fileoutputs\\" + fileName); // <-- WARNING!
            // ^^⚠ above, replace with your own directory path!
            if (file.exists()) {
                throw new IOException("File already exists");
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(fileContent);
            bw.newLine();
            bw.append("File created: ");
            bw.append(SimpleDateFormat.getDateTimeInstance().format(new Date()));
            bw.flush();
            bw.close();
            System.out.println("File created successfully");

        }
    }
}
