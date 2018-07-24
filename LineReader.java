package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class LineReader implements Iterable<String>, AutoCloseable, FLineReader {

    private BufferedReader reader;
    private String nextLine;

    public LineReader(String filename) throws IOException {
        try {
            reader = Files.newBufferedReader(Paths.get(filename));
            nextLine = reader.readLine();
        } catch (IOException ex) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex2) {
                    //silent catch
                } finally {
                    reader = null;
                }
            }
            throw ex;
        }
    }

    @Override
    public void close() {
        if (reader != null){
            try{
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                reader = null;
            }
        }
    }

    @Override
    public boolean hasNextLine() {
        return nextLine != null;
    }

    @Override
    public String getNextLine() {
        if  (nextLine == null){
            throw new IllegalStateException();
        }

        try {
            String toReturn = nextLine;
            nextLine = reader.readLine();
            return toReturn;
        }
        catch (IOException ex){
            throw new IllegalStateException(ex);
        }
    }


    // implementation of the Iterable interface
    @Override
    public Iterator<String> iterator() {
        // Iterator is an anonymous implementation of the Iterator<T> interface
        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return LineReader.this.hasNextLine();
            }

            @Override
            public String next() {
                return LineReader.this.getNextLine();
            }
        };
    }
}