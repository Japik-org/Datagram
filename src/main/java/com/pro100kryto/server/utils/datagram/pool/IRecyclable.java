package com.pro100kryto.server.utils.datagram.pool;

public interface IRecyclable {
    void recycle();
    boolean isRecycled();
    void restore();
}
