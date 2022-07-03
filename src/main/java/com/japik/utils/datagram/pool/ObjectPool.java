package com.japik.utils.datagram.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ObjectPool <T extends IRecyclable>
        implements IPoolObjectCallback<T> {

    protected final int capacity;
    protected final APoolObjectFactory<T> factory;
    protected final BlockingQueue<T> pool;
    protected final ReentrantLock lock;

    public ObjectPool(int capacity, APoolObjectFactory<T> factory) {
        this.capacity = capacity;
        this.factory = factory;
        pool = new ArrayBlockingQueue<>(capacity);
        lock = new ReentrantLock();
        this.factory.pool = this;
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

    public T getNext() throws PoolEmptyException {
        final T o = pool.poll();

        if (o == null){
            throw PoolEmptyException.instance;
        }

        o.restore();
        return o;
    }

    public void put(T object){
        if (object.getStatus() != RecycleStatus.Recycled){
            object.recycle();
        }
        if (!pool.contains(object)) {
            pool.add(object);
        }
    }

    protected void fill(){
        for (int i = 0; i < capacity; i++) {
            pool.add(factory.createRecycled());
        }
    }

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