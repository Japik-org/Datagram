package com.pro100kryto.server.utils.datagram.packets;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.nio.channels.IllegalBlockingModeException;

public interface IPacketInProcess{
    InetAddress getAddress();
    int getPort();

    void setEndPoint(EndPoint endPoint);
    IEndPoint getEndPoint();

    DataContainer getDataContainer();
    DataCreator getDataCreator();

    IPacket convertToFinalPacket();

    PacketStatus getPacketStatus();

    void receive(DatagramSocket socket) throws IOException, SocketTimeoutException, PortUnreachableException, IllegalBlockingModeException;

    void recycle();
    boolean isRecycled();
    IPacketInProcess reset();

    enum PacketStatus{
        /** ready for write new data */
        Ready,
        /** containing received data, read only */
        Received,
        /** final packet for send, read only */
        Final,
        /** recycled, does not use anymore */
        Recycled
    }
}
