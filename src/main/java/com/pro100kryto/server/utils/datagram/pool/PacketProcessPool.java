package com.pro100kryto.server.utils.datagram.pool;

import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.utils.datagram.process.IPacketReader;
import com.pro100kryto.server.utils.datagram.process.PacketProcess;
import lombok.NonNull;

public class PacketProcessPool extends ObjectPool<PacketProcess>{

    public PacketProcessPool(int capacity, @NonNull IPacketReader packetReader, @NonNull ILogger logger) {
        super(capacity, new PoolPacketProcessFactory(packetReader, logger));
    }
}
