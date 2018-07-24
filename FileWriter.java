package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileWriter implements AutoCloseable, WriteInterface{
    private BufferedWriter write;
   // private String writ;
//    private LineReader reader
//    private  oPath;

    public FileWriter(String path) {
        Path oPath = Paths.get(path);
        try {
            write = Files.newBufferedWriter(oPath, StandardOpenOption.CREATE);
        } catch (IOException iox) {
            iox.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void writeID(String text) throws IOException {
        if(text!=null) {
            write.write(text);
            //write.flush();
            write.newLine();
        }
        else {
            try {
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

