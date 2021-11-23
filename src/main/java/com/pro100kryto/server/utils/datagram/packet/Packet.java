package com.pro100kryto.server.utils.datagram.packet;

import com.pro100kryto.server.utils.datagram.pool.IRecyclable;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;

import java.io.Closeable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class Packet implements IRecyclable, Closeable {
    @Getter(onMethod_={@Synchronized}) @Setter(onMethod_={@Synchronized})
    private PacketStatus packetStatus;
    @Getter
    protected final ByteBuffer buffer;
    @Getter @Setter
    protected EndPoint endPoint;

    public Packet(int capacity) {
        this(capacity, null, 0);
    }

    /**
     * create new packet
     */
    public Packet(int capacity, InetAddress address, int port){
        packetStatus = PacketStatus.Ready;
        buffer = ByteBuffer.allocate(capacity);
        endPoint = new EndPoint(address, port);
    }

    public final void putInDatagramPacket(DatagramPacket datagramPacketOut) {
        datagramPacketOut.setData(buffer.array(), buffer.arrayOffset(), buffer.position());
        datagramPacketOut.setAddress(endPoint.getAddress());
        datagramPacketOut.setPort(endPoint.getPort());
    }

    public final DatagramPacket asDatagramPacket() {
        return new DatagramPacket(
                buffer.array(),
                buffer.arrayOffset(),
                buffer.position(),
                endPoint.getAddress(),
                endPoint.getPort());
    }

    public void recycle() {
        buffer.clear();
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