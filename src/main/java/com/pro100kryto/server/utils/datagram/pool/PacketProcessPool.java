package com.pro100kryto.server.utils.datagram.pool;

import com.pro100kryto.server.utils.datagram.process.IPacketReader;
import com.pro100kryto.server.utils.datagram.process.PacketProcess;
import lombok.NonNull;

public class PacketProcessPool extends ObjectPool<PacketProcess>{

    public PacketProcessPool(int capacity, @NonNull IPacketReader packetReader) {
        super(capacity, new PoolPacketProcessFactory(packetReader));
    }
}
