package com.japik.utils.datagram.pool;

import java.io.Closeable;
import java.io.IOException;

public interface IRecyclable extends Closeable {
    void recycle();
    void restore();
    void destroy();

    RecycleStatus getStatus();

    @Override
    default void close() throws IOException {
        recycle();
    }
}
