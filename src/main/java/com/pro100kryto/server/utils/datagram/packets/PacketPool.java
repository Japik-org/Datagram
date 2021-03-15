package com.pro100kryto.server.utils.datagram.packets;

import com.pro100kryto.server.utils.datagram.exceptions.PoolEmptyException;
import com.pro100kryto.server.utils.datagram.objectpool.ObjectPool;
import org.jetbrains.annotations.Nullable;

public class PacketPool extends ObjectPool<Packet> {
    private final int packetSize;

    public PacketPool(int capacity, int packetSize) {
        super(capacity);
        this.packetSize = packetSize;
    }

    @Override
    protected Packet createRecycledObject() {
        return new PacketInPool(packetSize);
    }

    @Nullable
    public IPacketInProcess getPacket(){
        return get();
    }

    @Nullable
    public IPacketInProcess getNextPacketOrNull(){
        try {
            return nextAndGet();
        } catch (PoolEmptyException poolEmptyException) {
            return null;
        }
    }

    public int getPacketSize() {
        return packetSize;
    }

    protected final class PacketInPool extends Packet {
        /**
         * create new packet
         */
        public PacketInPool(int packetSize) {
            super(new DataContainer(packetSize));
        }

        /**
         * @throws IllegalStateException when is already recycled
         */
        @Override
        public void recycle() {
            if (getPacketStatus() == PacketStatus.Recycled)
                throw new IllegalStateException("Already recycled");
            super.recycle();
            pool.add(this);
        }

        public void recycleAndDelete(){
            super.recycle();
            pool.remove(this);
        }
    }
}
