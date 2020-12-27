package com.pro100kryto.server.utils.datagram.objectpool;

public interface IRecycle {
    void recycle();
    boolean isRecycled();
    void restore();
}
