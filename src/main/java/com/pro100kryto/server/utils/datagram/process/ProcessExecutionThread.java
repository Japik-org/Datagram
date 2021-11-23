package com.pro100kryto.server.utils.datagram.process;

import java.util.concurrent.ThreadFactory;

public final class ProcessExecutionThread implements IProcessExecution {
    private final Thread thread;

    public ProcessExecutionThread(Runnable runnable) {
        thread = new Thread(runnable);
    }

    public ProcessExecutionThread(Runnable runnable, ThreadFactory threadFactory) {
        thread = threadFactory.newThread(runnable);
    }

    @Override
    public synchronized void start() {
        thread.start();
    }

    @Override
    public synchronized void stop() {
        thread.stop();
    }

    @Override
    public boolean isDone() {
        return !thread.isAlive();
    }
}
