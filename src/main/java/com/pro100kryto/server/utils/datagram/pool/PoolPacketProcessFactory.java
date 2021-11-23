package com.pro100kryto.server.utils.datagram.pool;

import com.pro100kryto.server.utils.datagram.process.IPacketReader;
import com.pro100kryto.server.utils.datagram.process.PacketProcess;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicLong;

public final class PoolPacketProcessFactory extends APoolProcessFactory <PacketProcess> {

    @NonNull
    private final IPacketReader packetReader;

    public PoolPacketProcessFactory(@NotNull IPacketReader packetReader, AtomicLong idCounter) {
        super(idCounter);
        this.packetReader = packetReader;
    }

    public PoolPacketProcessFactory(@NotNull IPacketReader packetReader) {
        super();
        this.packetReader = packetReader;
    }

    @Override
    public PacketProcess createRecycled() {
        return new PoolPacketProcess(
                idCounter.incrementAndGet(),
                packetReader
        );
    }

    private final class PoolPacketProcess extends PacketProcess{

        public PoolPacketProcess(long id, @NonNull IPacketReader packetReader) {
            super(id, packetReader);
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
