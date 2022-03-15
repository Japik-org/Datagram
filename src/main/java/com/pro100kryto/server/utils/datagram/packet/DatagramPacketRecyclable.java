package com.pro100kryto.server.utils.datagram.packet;

import com.pro100kryto.server.utils.datagram.pool.IRecyclable;
import com.pro100kryto.server.utils.datagram.pool.RecycleStatus;
import lombok.Getter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

@Getter
public class DatagramPacketRecyclable implements IRecyclable {
    private RecycleStatus status;

    protected final ByteBuffer buffer;
    protected final DatagramPacket datagramPacket;

    public DatagramPacketRecyclable(int capacity) {
        status = RecycleStatus.Recycled;
        buffer = ByteBuffer.allocate(capacity);

        datagramPacket = new DatagramPacket(
                buffer.array(),
                0,
                buffer.capacity()
        );
    }

    public DatagramPacketRecyclable(int capacity, InetSocketAddress address){
        status = RecycleStatus.Recycled;
        buffer = ByteBuffer.allocate(capacity);

        datagramPacket = new DatagramPacket(
                buffer.array(),
                0,
                buffer.capacity(),
                address
        );
    }

    @Override
    public final synchronized RecycleStatus getStatus() {
        return status;
    }

    protected final synchronized void setStatus(RecycleStatus status) {
        this.status = status;
    }

    public DatagramPacket receive(DatagramSocket datagramSocket) throws IOException {
        datagramSocket.receive(datagramPacket);
        return datagramPacket;
    }

    public void recycle() {
        buffer.clear();
        datagramPacket.setData(buffer.array(), 0, buffer.capacity());
        setStatus(RecycleStatus.Recycled);
    }

    public void restore() {
        setStatus(RecycleStatus.Restored);
    }

    @Override
    public void destroy() {
        setStatus(RecycleStatus.Destroyed);
    }
}