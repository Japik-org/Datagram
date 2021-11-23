package com.pro100kryto.server.utils.datagram.packet;

import java.util.Stack;

public class DataContainerPool {
    private final int dataLength;
    private final Stack<DataContainerInPool> pool;
    private int count;

    /**
     *
     * @param dataLength byte[] size
     * @param initialCapacity stack capacity
     * @param fillFactor belongs [0;1]
     */
    public DataContainerPool(int dataLength, int initialCapacity, float fillFactor) {
        this.pool = new Stack<>();
        pool.ensureCapacity(initialCapacity);
        this.dataLength = dataLength;
        for (int i = initialCapacity - (int)(initialCapacity*fillFactor);
             i < initialCapacity; i++) {
            pool.push(new DataContainerInPool(dataLength));
            count++;
        }
    }

    /**
     * get next container
     */
    public synchronized DataContainer remove(){
        if (pool.empty())
            return new DataContainerInPool(dataLength);
        count--;
        return pool.pop();
    }

    public boolean isEmpty(){
        return count<1;
    }

    public int getDataLength() {
        return dataLength;
    }

    private final class DataContainerInPool extends DataContainer {
        public DataContainerInPool(int bufferSize) {
            super(bufferSize);
        }

        @Override
        public synchronized void reset() {
            super.reset();
            pool.push(this);
            count++;
        }
    }
}
