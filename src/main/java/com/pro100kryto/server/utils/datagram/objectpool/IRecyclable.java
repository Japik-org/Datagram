package com.pro100kryto.server.utils.datagram.objectpool;

public interface IRecyclable {
    void recycle();
    boolean isRecycled();
    void restore();
}
