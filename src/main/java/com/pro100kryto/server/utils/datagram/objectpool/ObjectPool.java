package com.pro100kryto.server.utils.datagram.objectpool;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public abstract class ObjectPool <T extends IRecyclable> {
    protected final int capacity;
    protected final BlockingQueue<T> pool;
    protected final ReentrantLock lock;
    @Nullable
    private T nextObject = null;

    public ObjectPool(int capacity) {
        this.capacity = capacity;
        pool = new ArrayBlockingQueue<>(capacity);
        lock = new ReentrantLock();
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
     * load next packet from the pool if is necessary
     */
    public void next() {
        lock.lock();
        try {
            if (nextObject == null) {
                nextObject = pool.poll();
            }
        } finally {
            lock.unlock();
        }
    }

    @Nullable
    public T get() {
        final T o;

        lock.lock();
        try {
            o = nextObject;
            nextObject = null;
        } finally {
            lock.unlock();
        }

        if (o == null){
            return null;
        }

        o.restore();
        return o;
    }

    @Nullable
    public T nextAndGet() {
        final T o = pool.poll();

        if (o == null){
            return null;
        }

        o.restore();
        return o;
    }

    public void put(T object){
        if (!object.isRecycled()){
            object.recycle();
        }
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

    public boolean remove(T o){
        return pool.remove(o);
    }
}