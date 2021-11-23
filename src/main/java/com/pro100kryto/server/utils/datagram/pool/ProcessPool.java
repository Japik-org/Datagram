package com.pro100kryto.server.utils.datagram.pool;

import com.pro100kryto.server.utils.datagram.process.Process;

public class ProcessPool extends ObjectPool<Process> {

    public ProcessPool(int capacity, APoolProcessFactory<Process> factory) {
        super(capacity, factory);
    }
}
