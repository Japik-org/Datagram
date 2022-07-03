package com.japik.utils.datagram.pool;

public abstract class APoolObjectFactory<O extends IRecyclable>{
    protected IPoolObjectCallback<O> pool;

    public abstract O createRecycled();
}
