package com.pro100kryto.server.utils.datagram.packetprocess2;

public abstract class Processor <T extends Process, R> {

    public final R start(T process){
        beforeStart(process);
        R ret = starting(process);
        afterStart(process);
        return ret;
    }

    protected void beforeStart(T process){}
    protected abstract R starting(T process);
    protected void afterStart(T process){}

    /**
     * try starting process and recycling on fail
     * @param process
     * @return object if succ started or null if failed start and was recycled
     */
    public final R startOrRecycle(T process){
        try {
            return start(process);
        } catch (Throwable ignored){
        }
        process.recycle();
        return null;
    }
}
