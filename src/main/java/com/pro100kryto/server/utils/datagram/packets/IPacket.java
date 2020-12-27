package com.pro100kryto.server.utils.datagram.packets;

import java.net.DatagramPacket;
import java.net.InetAddress;

public interface IPacket {
    InetAddress getAddress();
    int getPort();

    IEndPoint getEndPoint();

    DataReader getDataReader();
    void putInDatagramPacket(final DatagramPacket datagramPacketOut);
    DatagramPacket createDatagramPacket();

    void recycle();
    boolean isRecycled();
    IPacketInProcess reset();

    IPacket clone();
}
