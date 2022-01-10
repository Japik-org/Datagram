package com.pro100kryto.server.utils.datagram.process;


import com.pro100kryto.server.utils.datagram.packet.DatagramPacketWrapper;

public interface IPacketReader {
    void read(DatagramPacketWrapper packet) throws Throwable;
}
