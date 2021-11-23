package com.pro100kryto.server.utils.datagram.process;


import com.pro100kryto.server.utils.datagram.packet.Packet;

public interface IPacketReader {
    void read(Packet packet) throws Throwable;
}
