package com.pro100kryto.server.utils.datagram.packetprocess2;

import com.pro100kryto.server.utils.datagram.objectpool.ObjectPool;
import com.pro100kryto.server.utils.datagram.packets.IPacket;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;
import org.jetbrains.annotations.Nullable;

public abstract class ProcessPacketInThreadPool
        <T extends ProcessPacketInThreadPool<?>>
        extends ProcessInThreadPool<T>{

    protected final IntObjectHashMap<IPacketProcess> packetIdPacketProcessMap;
    protected IPacket packet = null;


    protected ProcessPacketInThreadPool(ObjectPool<T> processPool,
                                        boolean recycleAfterRun,
                                        IntObjectHashMap<IPacketProcess> packetIdPacketProcessMap) {
        super(processPool, recycleAfterRun);
        this.packetIdPacketProcessMap = packetIdPacketProcessMap;
    }

    public void setPacket(IPacket packet){
        this.packet = packet;
    }

    /**
     *
     * @return null if process was recycled
     */
    @Nullable
    public IPacket getPacket() {
        return packet;
    }

    /**
     * @throws IllegalStateException if already recycled
     */
    @Override
    public synchronized void recycle() throws IllegalStateException {
        super.recycle();
        packet.recycle();
        packet = null;
    }

    @Override
    protected void running() {
        IPacketProcess packetProcess = getPacketProcess();
        try {
            packetProcess.run(packet);
        } catch (NullPointerException ignored){
        }
    }

    @Nullable
    protected abstract IPacketProcess getPacketProcess();
}
