package com.pro100kryto.server.utils.datagram.packetprocess2;

import com.pro100kryto.server.utils.datagram.objectpool.ObjectPool;

public abstract class ProcessInPool<T extends ProcessInPool<?>> extends Process {
    protected final ObjectPool<T> processPool;
    private boolean recycleAfterRun;

    protected ProcessInPool(ObjectPool<T> processPool, boolean recycleAfterRun) {
        this.processPool = processPool;
        this.recycleAfterRun = recycleAfterRun;
    }

    @Override
    protected void afterRun() {
        if (recycleAfterRun) this.recycle();
    }

    public final void setRecycleAfterRun(boolean recycleAfterRun){
        this.recycleAfterRun = recycleAfterRun;
    }

    /**
     * @throws IllegalStateException if already recycled
     */
    @Override
    public synchronized void recycle() throws IllegalStateException {
        super.recycle(); // throw
        poolPutThis();
    }

    @Override
    public synchronized void restore() {
        poolRemoveThis();
        super.restore();
    }

    protected abstract void poolPutThis();
    protected abstract boolean poolContainsThis();
    protected abstract void poolRemoveThis();
}
