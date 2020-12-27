package com.pro100kryto.server.utils.datagram.objectpool;

public final class RecycleContainer<T> implements IRecycle{
    private final ObjectPool<RecycleContainer<T>> pool;
    private T object;
    private boolean isRecycled = true;

    public RecycleContainer(ObjectPool<RecycleContainer<T>> pool, T object) {
        this.pool = pool;
        this.object = object;
    }

    @Override
    public synchronized void recycle() {
        isRecycled = true;
        pool.put(this);
    }

    @Override
    public synchronized boolean isRecycled() {
        return isRecycled;
    }

    @Override
    public synchronized void restore() {
        isRecycled = false;
    }

    public T getObject() {
        return object;
    }
    public void setObject(T object){
        this.object = object;
    }
}
