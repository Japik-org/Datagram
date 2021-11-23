package com.pro100kryto.server.utils.datagram.pool;

import com.pro100kryto.server.utils.datagram.packet.Packet;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class PoolPacketFactory extends APoolObjectFactory<Packet> {
    private final int packetCapacity;

    @Override
    public Packet createRecycled() {
        return new PoolPacket(packetCapacity);
    }

    private final class PoolPacket extends Packet {
        /**
         * create new packet
         */
        public PoolPacket(int packetSize) {
            super(packetSize);
        }

        @Override
        public void restore() {
            super.restore();
            pool.remove(this);
        }

        @Override
        public void recycle() {
            super.recycle();
            pool.put(this);
        }

        public void recycleAndRemove(){
            super.recycle();
            pool.remove(this);
        }
    }
}
