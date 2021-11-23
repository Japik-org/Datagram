package com.pro100kryto.server.utils.datagram.process;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ExecutorServiceProcessor implements IProcessor<Process>{
    @Getter
    private final ExecutorService executor;

    public ExecutorServiceProcessor(ExecutorService executor) {
        this.executor = executor;
    }

    public ExecutorServiceProcessor(int nThreads){
        executor = Executors.newFixedThreadPool(nThreads);
    }

    @Override
    public IProcessExecution startProcess(Process process) {
        return process.submit(executor);
    }
}
