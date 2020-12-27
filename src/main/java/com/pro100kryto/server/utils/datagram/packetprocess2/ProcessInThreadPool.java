package com.pro100kryto.server.utils.datagram.packetprocess2;

import com.pro100kryto.server.utils.datagram.objectpool.ObjectPool;

import java.util.concurrent.Future;

public abstract class ProcessInThreadPool<T extends ProcessInThreadPool<?>> extends ProcessInPool<T> {
    private volatile Future<?> future;

    protected ProcessInThreadPool(ObjectPool<T> processPool, boolean recycleAfterRun) {
        super(processPool, recycleAfterRun);
    }

    public Future<?> getFuture() {
        return future;
    }

    public void setFuture(Future<?> future) {
        this.future = future;
    }
}
