package com.pro100kryto.server.utils.datagram.packetprocess;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class PacketProcessExecutor extends PacketProcessAbstract{
    protected final ExecutorService executorService;
    private Future<?> future;

    public PacketProcessExecutor(IPacketProcessCallback processCallback, int id,
                                 ExecutorService executorService) {
        super(processCallback, id);
        this.executorService = executorService;
    }

    @Override
    protected void startAction() throws Throwable {
        future = executorService.submit(this);
    }

    @Override
    protected void stopAction(boolean force) throws Throwable {
        if (future.cancel(force) || future.isDone()) return;
        throw new Exception("PacketProcess cannot be stopped");
    }
}
