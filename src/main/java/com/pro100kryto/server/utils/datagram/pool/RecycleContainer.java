package com.pro100kryto.server.utils.datagram.pool;

import lombok.Getter;
import lombok.Setter;

public final class RecycleContainer<T> implements IRecyclable {
    private final ObjectPool<RecycleContainer<T>> pool;
    @Getter @Setter
    private T object;
    @Getter
    private RecycleStatus status;

    public RecycleContainer(ObjectPool<RecycleContainer<T>> pool, T object) {
        this.pool = pool;
        this.object = object;
    }

    @Override
    public synchronized void recycle() {
        status = RecycleStatus.Recycled;
        pool.put(this);
    }

    @Override
    public synchronized void restore() {
        status = RecycleStatus.Restored;
    }

    @Override
    public void destroy() {
        status = RecycleStatus.Destroyed;
    }
}
