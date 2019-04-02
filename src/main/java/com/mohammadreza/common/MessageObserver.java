package com.mohammadreza.common;

import java.io.IOException;

/**
 * This is an interface for Observer Design pattern
 */
@FunctionalInterface
public interface MessageObserver {
    void updateObserver(String message) throws IOException, ApplicationException;
}
