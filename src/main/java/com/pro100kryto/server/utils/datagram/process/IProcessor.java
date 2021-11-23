package com.pro100kryto.server.utils.datagram.process;

import org.jetbrains.annotations.Nullable;

public interface IProcessor <P extends Process> {
    IProcessExecution startProcess(P process) throws Exception;

    @Nullable
    default IProcessExecution startOrRecycle(P process){
        try {
            return startProcess(process);
        } catch (Throwable ignored){
        }
        process.recycle();
        return null;
    }
}
