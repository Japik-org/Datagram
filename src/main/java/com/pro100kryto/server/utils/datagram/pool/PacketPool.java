package com.pro100kryto.server.utils.datagram.pool;

import com.pro100kryto.server.utils.datagram.packet.Packet;
import lombok.Getter;

public class PacketPool extends ObjectPool<Packet> {
    @Getter
    protected final int packetSize;

    public PacketPool(int capacity, int packetSize) {
        super(capacity, new PoolPacketFactory(packetSize));
        this.packetSize = packetSize;
    }
}
