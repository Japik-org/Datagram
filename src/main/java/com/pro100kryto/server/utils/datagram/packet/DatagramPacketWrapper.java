package com.pro100kryto.server.utils.datagram.packet;

import com.pro100kryto.server.utils.datagram.pool.IRecyclable;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class DatagramPacketWrapper implements IRecyclable, Closeable {
    @Getter(onMethod_={@Synchronized}) @Setter(onMethod_={@Synchronized})
    private PacketStatus packetStatus;

    @Getter
    protected final ByteBuffer buffer;

    @Getter
    protected final DatagramPacket datagramPacket;

    public DatagramPacketWrapper(int capacity) {
        packetStatus = PacketStatus.Ready;
        buffer = ByteBuffer.allocate(capacity);

        datagramPacket = new DatagramPacket(
                buffer.array(),
                0,
                buffer.capacity()
        );
    }

    /**
     * create new packet
     */
    public DatagramPacketWrapper(int capacity, InetSocketAddress address){
        packetStatus = PacketStatus.Ready;
        buffer = ByteBuffer.allocate(capacity);

        datagramPacket = new DatagramPacket(
                buffer.array(),
                0,
                buffer.capacity(),
                address
        );
    }

    public DatagramPacket receive(DatagramSocket datagramSocket) throws IOException {
        datagramSocket.receive(datagramPacket);
        setPacketStatus(PacketStatus.Received);
        return datagramPacket;
    }

    public void recycle() {
        buffer.clear();
        datagramPacket.setData(buffer.array(), 0, buffer.capacity());
        setPacketStatus(PacketStatus.Recycled);
    }

    public final boolean isRecycled() {
        return getPacketStatus() == PacketStatus.Recycled;
    }

    public void restore() {
        setPacketStatus(PacketStatus.Ready);
    }

    /**
     * recycle
     */
    @Override
    public void close() {
        this.recycle();
    }
}