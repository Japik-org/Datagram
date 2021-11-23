package com.pro100kryto.server.utils.datagram.process;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Future;

@RequiredArgsConstructor
public final class FutureProcessExecution implements IProcessExecution {
    @Getter
    private final Future<?> future;

    @Override
    public void start() {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void stop() {
        future.cancel(true);
    }

    @Override
    public boolean isDone() {
        return future.isDone();
    }
}
