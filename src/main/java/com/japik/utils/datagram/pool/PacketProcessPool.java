package com.japik.utils.datagram.pool;

import com.japik.logger.ILogger;
import com.japik.utils.datagram.process.IPacketReader;
import com.japik.utils.datagram.process.PacketProcess;
import lombok.NonNull;

public class PacketProcessPool extends ObjectPool<PacketProcess>{

    public PacketProcessPool(int capacity, @NonNull IPacketReader packetReader, @NonNull ILogger logger) {
        super(capacity, new PoolPacketProcessFactory(packetReader, logger));
    }
}
