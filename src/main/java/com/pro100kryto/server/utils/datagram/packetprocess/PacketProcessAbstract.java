package com.pro100kryto.server.utils.datagram.packetprocess;

import com.pro100kryto.server.IStartStopAlive;
import com.pro100kryto.server.StartStopStatus;
import com.pro100kryto.server.utils.datagram.exceptions.WrongDataException;
import com.pro100kryto.server.utils.datagram.packets.IPacketInProcess;

public abstract class PacketProcessAbstract implements IStartStopAlive, Runnable {
    private StartStopStatus status;
    protected final IPacketProcessCallback processCallback;
    private final int id;

    public PacketProcessAbstract(IPacketProcessCallback processCallback, int id) {
        this.processCallback = processCallback;
        this.id = id;
    }

    public final int getId() {
        return id;
    }

    @Override
    public synchronized final void start() throws Throwable {
        if (status != StartStopStatus.STOPPED) throw new IllegalStateException("Is not stopped");
        status = StartStopStatus.STARTING;
        startAction();
        status = StartStopStatus.STARTED;
    }

    @Override
    public synchronized final void stop(boolean force) throws Throwable {
        status = StartStopStatus.STOPPING;
        stopAction(force);
        processCallback.onPacketProcessFinished(getId());
        status = StartStopStatus.STOPPED;
    }

    @Override
    public final void run(){
        try {
            runProcess();
        } catch (WrongDataException wrongDataException){
            IPacketInProcess newPacket = processCallback.getNewPacket();
            packetOnWrongData(newPacket);
            processCallback.sendPacket(newPacket);
        } catch (Throwable e){
            processCallback.getLogger().writeException(e, this.getClass().getCanonicalName()+" run error");
        }
        processCallback.onPacketProcessFinished(getId());
    }

    @Override
    public StartStopStatus getStatus() {
        return status;
    }

    protected abstract void startAction() throws Throwable;
    protected abstract void stopAction(boolean force) throws Throwable;
    protected abstract void runProcess() throws WrongDataException, Throwable;
    protected abstract void packetOnWrongData(IPacketInProcess outPacket);
}