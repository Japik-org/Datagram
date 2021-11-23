package com.pro100kryto.server.utils.datagram.pool;

public interface IPoolObjectCallback<O extends IRecyclable> {
    void put(O o);
    boolean remove(O o);
}
