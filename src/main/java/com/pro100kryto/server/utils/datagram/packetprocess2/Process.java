package com.pro100kryto.server.utils.datagram.packetprocess2;

import com.pro100kryto.server.utils.datagram.objectpool.IRecycle;

public abstract class Process implements Runnable, IRecycle {
    private static final IllegalStateException ILLEGAL_STATE_EXCEPTION = new IllegalStateException("Already recycled");
    private boolean isRecycled = true;

    @Override
    public final void run() {
        beforeRun();
        running();
        afterRun();
    }
    protected void beforeRun(){}
    protected abstract void running();
    protected void afterRun(){}

    /**
     * @throws IllegalStateException if already recycled
     */
    @Override
    public void recycle() throws IllegalStateException {
        if (isRecycled) throw ILLEGAL_STATE_EXCEPTION;
        isRecycled = true;
    }

    @Override
    public final boolean isRecycled() {
        return isRecycled;
    }

    @Override
    public void restore() {
        isRecycled = false;
    }
}
