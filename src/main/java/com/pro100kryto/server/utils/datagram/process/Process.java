package com.pro100kryto.server.utils.datagram.process;

import com.pro100kryto.server.utils.datagram.pool.IRecyclable;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

public class Process implements IRecyclable, Closeable {

    @Getter
    protected final long id;

    @Getter
    protected boolean isRecycled = true;
    @Getter
    protected IProcessExecution processExecution;

    @Setter @Getter
    @Nullable
    protected Runnable runnable;

    public Process(long id, @NotNull Runnable runnable) {
        this.id = id;
        this.runnable = runnable;
    }

    protected Process(long id) {
        this.id = id;
        this.runnable = null;
    }

    public IProcessExecution createThread(){
        return (processExecution = new ThreadProcessExecution(
                runnable
        ));
    }

    public IProcessExecution createThread(ThreadFactory threadFactory){
        return (processExecution = new ThreadProcessExecution(
                runnable,
                threadFactory
        ));
    }

    /**
     * @throws java.util.concurrent.RejectedExecutionException
     */
    public IProcessExecution submit(ExecutorService executor){
        return (processExecution = new FutureProcessExecution(
                executor.submit(runnable)
        ));
    }

    @Override
    public void recycle() {
        if (!processExecution.isDone()){
            processExecution.stop();
        }
        isRecycled = true;
    }

    @Override
    public void restore() {
        isRecycled = false;
    }

    @Override
    public void close() throws IOException {
        this.recycle();
    }
}
