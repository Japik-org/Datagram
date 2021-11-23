package com.pro100kryto.server.utils.datagram.process;

import org.jetbrains.annotations.Nullable;

public interface IProcessor <P extends Process> {
    IProcessExecution start(P process);

    @Nullable
    default IProcessExecution startOrRecycle(P process){
        try {
            return start(process);
        } catch (Throwable ignored){
        }
        process.recycle();
        return null;
    }

    default IProcessExecution startThenRecycle(P process){
        try {
            return start(process);
        } finally {
            process.recycle();
        }
    }
}
