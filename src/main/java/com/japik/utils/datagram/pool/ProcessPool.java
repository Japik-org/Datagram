package com.japik.utils.datagram.pool;

import com.japik.utils.datagram.process.Process;

public class ProcessPool extends ObjectPool<Process> {

    public ProcessPool(int capacity, APoolProcessFactory<Process> factory) {
        super(capacity, factory);
    }
}
