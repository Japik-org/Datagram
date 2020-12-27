package com.pro100kryto.server.utils.datagram.objectpool;

import com.pro100kryto.server.utils.datagram.exceptions.PoolEmptyException;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class ObjectPool <T extends IRecycle> {
    protected final int capacity;
    protected final BlockingQueue<T> pool;
    private T nextObject = null;

    private static final PoolEmptyException POOL_EMPTY_EXCEPTION = new PoolEmptyException();

    public ObjectPool(int capacity) {
        this.capacity = capacity;
        pool = new ArrayBlockingQueue<>(capacity);
    }

    /**
     * will clear pool if is not empty and fill. Recommended clear manually and check what is not in use before refill.
     */
    public void refill(){
        if (!isEmpty()) {
            clear();
        }
        fill();
    }

    /**
     * load next packet from pool
     */
    public synchronized void next() throws PoolEmptyException {
        try {
            nextObject = pool.poll();
            nextObject.restore();
            return;
        } catch (NullPointerException ignored){
        }
        throw POOL_EMPTY_EXCEPTION;
    }

    public synchronized T get() {
        return nextObject;
    }

    public synchronized T nextAndGet() throws PoolEmptyException {
        next();
        return get();
    }

    /**
     * @throws IllegalStateException pool is full
     */
    public void put(T object){
        if (!object.isRecycled() || pool.size()>=capacity) throw new IllegalStateException();
        pool.add(object);
    }

    protected void fill(){
        for (int i = 0; i < capacity; i++) {
            pool.add(createRecycledObject());
        }
    }

    protected abstract T createRecycledObject();

    public boolean isEmpty(){
        return pool.isEmpty();
    }

    /**
     * @return true if not all objects was recycled
     */
    public boolean isInUse(){
        return pool.size() != capacity;
    }

    public void clear(){
        for (int i=0; i==0 || (i<3 && isInUse()); i++) {
            while (!pool.isEmpty()) {
                pool.poll();
            }
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }
        pool.clear();
        nextObject = null;
    }

    public int getMaxCapacity() {
        return capacity;
    }

    public int getRemainingCapacity(){
        return pool.remainingCapacity();
    }

    public boolean contains(T o){
        return pool.contains(o);
    }

    public synchronized boolean remove(T o){
        return pool.remove(o);
    }
}