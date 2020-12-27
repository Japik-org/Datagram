package com.pro100kryto.server.utils.datagram.packets;

import com.pro100kryto.server.utils.datagram.objectpool.IRecycle;

import java.io.IOException;
import java.net.*;
import java.nio.channels.IllegalBlockingModeException;

public class Packet implements IPacket, IPacketInProcess, IRecycle {
    private PacketStatus packetStatus;
    private final DataCreator dataCreator;
    private final DataContainer dataContainer;
    private EndPoint endPoint;

    public Packet(DataContainer data) {
        this(data, null, 0);
    }

    /**
     * create new packet
     */
    public Packet(DataContainer data, InetAddress address, int port){
        this.dataContainer = data;
        endPoint = new EndPoint(address, port);
        dataCreator = new DataCreator(this.dataContainer);
        packetStatus = PacketStatus.Ready;
    }

    @Override
    public void putInDatagramPacket(DatagramPacket datagramPacketOut) {
        dataContainer.putDataInDatagramPacket(datagramPacketOut);
        datagramPacketOut.setAddress(getAddress());
        datagramPacketOut.setPort(getPort());
    }

    @Override
    public DatagramPacket createDatagramPacket() {
        return new DatagramPacket(
                dataContainer.getRaw(),
                dataCreator.getDataLength(),
                getAddress(),
                getPort());
    }

    @Override
    public IPacket convertToFinalPacket() {
        setPacketStatus(PacketStatus.Final);
        return this;
    }

    @Override
    public synchronized PacketStatus getPacketStatus() {
        return packetStatus;
    }

    protected synchronized void setPacketStatus(PacketStatus packetStatus){
        this.packetStatus = packetStatus;
    }

    @Override
    public void receive(DatagramSocket socket)
            throws IOException, SocketTimeoutException, PortUnreachableException, IllegalBlockingModeException {

        DatagramPacket datagramPacket = new DatagramPacket(getDataContainer().getRaw(), 0, getDataContainer().getBufferSize());
        socket.receive(datagramPacket);
        setEndPoint(new EndPoint(datagramPacket.getAddress(), datagramPacket.getPort()));
        setPacketStatus(PacketStatus.Received);
    }

    @Override
    public void setEndPoint(EndPoint endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public IEndPoint getEndPoint(){
        return endPoint;
    }

    @Override
    public InetAddress getAddress() {
        return endPoint.getAddress();
    }

    @Override
    public int getPort() {
        return endPoint.getPort();
    }

    @Override
    public DataContainer getDataContainer() {
        return dataContainer;
    }

    @Override
    public DataCreator getDataCreator() {
        return dataCreator;
    }

    @Override
    public DataReader getDataReader() {
        return dataCreator;
    }

    @Override
    public void recycle() {
        dataCreator.reset();
        setPacketStatus(PacketStatus.Recycled);
    }

    @Override
    public boolean isRecycled() {
        return getPacketStatus()==PacketStatus.Recycled;
    }

    @Override
    public void restore() {
        setPacketStatus(PacketStatus.Ready);
    }

    @Override
    public IPacketInProcess reset() {
        dataCreator.reset();
        return this;
    }

    @Override
    public IPacket clone() {
        Packet packet = new Packet(
                dataContainer.clone(),
                getAddress(),
                getPort());
        packet.packetStatus = packetStatus;
        return packet;
    }
}