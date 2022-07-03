package com.japik.utils.datagram.pool;

import com.japik.utils.datagram.packet.DatagramPacketRecyclable;
import lombok.Getter;

public class PacketPool extends ObjectPool<DatagramPacketRecyclable> {
    @Getter
    protected final int packetSize;

    public PacketPool(int capacity, int packetSize) {
        super(capacity, new PoolPacketFactory(packetSize));
        this.packetSize = packetSize;
    }
}
