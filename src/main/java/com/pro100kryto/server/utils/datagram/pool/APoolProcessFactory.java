package com.pro100kryto.server.utils.datagram.pool;

import com.pro100kryto.server.utils.datagram.process.Process;

import java.util.concurrent.atomic.AtomicLong;

public abstract class APoolProcessFactory<P extends Process>
        extends APoolObjectFactory<P> {

    protected final AtomicLong idCounter;


    public APoolProcessFactory(AtomicLong idCounter) {
        this.idCounter = idCounter;
    }

    public APoolProcessFactory() {
        this.idCounter = new AtomicLong();
    }
}
