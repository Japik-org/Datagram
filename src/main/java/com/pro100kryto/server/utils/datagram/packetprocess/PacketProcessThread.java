package com.pro100kryto.server.utils.datagram.packetprocess;


import com.pro100kryto.server.StartStopStatus;

public abstract class PacketProcessThread extends PacketProcessAbstract{
    private final Thread thread;

    public PacketProcessThread(IPacketProcessCallback processCallback, int id) {
        super(processCallback, id);
        thread = new Thread(this);
    }

    @Override
    public void startAction() throws Throwable {
        thread.start();
    }

    @Override
    public void stopAction(boolean force) throws Throwable {
        if (thread!=null && thread.isAlive()) thread.interrupt();
    }

    @Override
    public StartStopStatus getStatus() {
        if (thread.isAlive()) return StartStopStatus.STARTED;
        return super.getStatus();
    }
}
