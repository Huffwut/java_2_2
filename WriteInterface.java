package com.company;

import java.io.IOException;

public interface WriteInterface {
    void writeID(String text) throws IOException;
    void close();
}
