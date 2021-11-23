package com.pro100kryto.server.utils.datagram.process;

public final class ThreadProcessor implements IProcessor<Process>{

    @Override
    public IProcessExecution startProcess(Process process) {
        final IProcessExecution execution = process.createThread();
        execution.start();
        return execution;
    }
}
