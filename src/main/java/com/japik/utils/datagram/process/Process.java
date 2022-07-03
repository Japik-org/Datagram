package com.japik.utils.datagram.process;

import com.japik.utils.datagram.pool.IRecyclable;
import com.japik.utils.datagram.pool.RecycleStatus;
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
    protected RecycleStatus status;
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
        status = RecycleStatus.Recycled;
    }

    @Override
    public void restore() {
        status = RecycleStatus.Restored;
    }

    @Override
    public void destroy() {
        status = RecycleStatus.Destroyed;
    }

    @Override
    public void close() throws IOException {
        this.recycle();
    }
}
