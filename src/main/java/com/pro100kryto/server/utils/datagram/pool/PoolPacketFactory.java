package com.pro100kryto.server.utils.datagram.pool;

import com.pro100kryto.server.utils.datagram.packet.DatagramPacketRecyclable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class PoolPacketFactory extends APoolObjectFactory<DatagramPacketRecyclable> {
    private final int packetCapacity;

    @Override
    public DatagramPacketRecyclable createRecycled() {
        return new PoolPacket(packetCapacity);
    }

    private final class PoolPacket extends DatagramPacketRecyclable {
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
