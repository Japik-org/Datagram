package com.pro100kryto.server.utils.datagram.process;

import com.pro100kryto.server.utils.datagram.packet.Packet;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

public class PacketProcess extends Process {

    @Getter @Setter
    @Nullable
    protected Packet packet = null;

    @NonNull
    protected final IPacketReader packetReader;

    public PacketProcess(long id, @NonNull IPacketReader packetReader) {
        super(id);
        this.packetReader = packetReader;
        runnable = () -> this.packetReader.read(packet);
    }

    @Override
    public synchronized void recycle() {
        super.recycle();
        if (packet != null) {
            packet.recycle();
            packet = null;
        }
    }

    @Override
    public synchronized void restore() {
        super.restore();
        if (packet != null){
            packet.restore();
        }
    }
}
