package com.japik.utils.datagram.process;

import com.japik.logger.ILogger;
import com.japik.utils.datagram.packet.DatagramPacketRecyclable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

public class PacketProcess extends Process implements Runnable {

    @NonNull
    protected final IPacketReader packetReader;
    @NonNull
    protected final ILogger logger;
    @Setter
    protected boolean recycleOnFinish;

    @Getter @Setter
    @Nullable
    protected DatagramPacketRecyclable packet = null;

    public PacketProcess(long id, @NonNull IPacketReader packetReader, @NonNull ILogger logger,
                         boolean recycleOnFinish){
        super(id);
        this.packetReader = packetReader;
        this.logger = logger;
        runnable = this;
        this.recycleOnFinish = recycleOnFinish;
    }

    public PacketProcess(long id, @NonNull IPacketReader packetReader, @NonNull ILogger logger) {
        this(id, packetReader, logger, true);
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

    @Override
    public void run() {

        try {
            packetReader.read(packet);
        } catch (Throwable e) {
            logger.exception(e, "Failed process packet");
        }

        if (recycleOnFinish){
            recycle();
        }
    }
}
