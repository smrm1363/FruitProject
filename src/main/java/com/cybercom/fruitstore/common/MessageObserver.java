package com.cybercom.fruitstore.common;

import java.io.IOException;

@FunctionalInterface
public interface MessageObserver {
    void update(String message) throws IOException;
}
