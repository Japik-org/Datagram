package com.pro100kryto.server.utils.datagram.pool;

public abstract class APoolObjectFactory<O extends IRecyclable>{
    protected IPoolObjectCallback<O> pool;

    public abstract O createRecycled();
}
