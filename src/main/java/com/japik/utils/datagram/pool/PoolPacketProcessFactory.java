package com.japik.utils.datagram.pool;

import com.japik.logger.ILogger;
import com.japik.utils.datagram.process.IPacketReader;
import com.japik.utils.datagram.process.PacketProcess;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicLong;

public final class PoolPacketProcessFactory extends APoolProcessFactory <PacketProcess> {

    @NonNull
    private final IPacketReader packetReader;

    @NonNull
    private final ILogger logger;

    public PoolPacketProcessFactory(@NotNull IPacketReader packetReader, @NonNull ILogger logger, AtomicLong idCounter) {
        super(idCounter);
        this.packetReader = packetReader;
        this.logger = logger;
    }

    public PoolPacketProcessFactory(@NotNull IPacketReader packetReader, @NonNull ILogger logger) {
        super();
        this.packetReader = packetReader;
        this.logger = logger;
    }

    @Override
    public PacketProcess createRecycled() {
        return new PoolPacketProcess(
                idCounter.incrementAndGet(),
                packetReader,
                logger
        );
    }

    private final class PoolPacketProcess extends PacketProcess{

        public PoolPacketProcess(long id, @NonNull IPacketReader packetReader, @NonNull ILogger logger) {
            super(id, packetReader, logger);
        }

        @Override
        public synchronized void recycle() {
            super.recycle();
            pool.put(this);
        }

        @Override
        public synchronized void restore() {
            super.restore();
            pool.remove(this);
        }
    }
}
