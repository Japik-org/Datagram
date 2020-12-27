package com.pro100kryto.server.utils.datagram.packetprocess2;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ProcessorThreadPool<T extends ProcessInThreadPool<?>>
        extends Processor<T, Future<?>> {

    protected final ThreadPoolExecutor executor;

    public ProcessorThreadPool(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    /**
     * @throws IllegalArgumentException if maxPoolSize <= 0
     */
    public ProcessorThreadPool(int maxPoolSize){
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxPoolSize);
        executor.setMaximumPoolSize(maxPoolSize);
        executor.setCorePoolSize(maxPoolSize);
    }

    /**
     * @throws java.util.concurrent.RejectedExecutionException
     * @throws NullPointerException if process is null
     */
    @Override
    protected final Future<?> starting(T process) {
        Future<?> future = executor.submit(process);
        process.setFuture(future);
        return future;
    }

    public final ThreadPoolExecutor getExecutor() {
        return executor;
    }
}
