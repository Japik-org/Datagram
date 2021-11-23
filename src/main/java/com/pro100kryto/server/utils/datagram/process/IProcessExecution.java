package com.pro100kryto.server.utils.datagram.process;

import java.io.Closeable;
import java.io.IOException;

public interface IProcessExecution extends Closeable {
    void start();
    void stop();
    boolean isDone();

    @Override
    default void close() throws IOException {
        if (!isDone()) {
            stop();
        }
    }
}
