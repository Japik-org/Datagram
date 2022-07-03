package com.japik.utils.datagram.pool;

public interface IPoolObjectCallback<O extends IRecyclable> {
    void put(O o);
    boolean remove(O o);
}
