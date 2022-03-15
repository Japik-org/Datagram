package com.pro100kryto.server.utils.datagram.process;


import com.pro100kryto.server.utils.datagram.packet.DatagramPacketRecyclable;

public interface IPacketReader {
    void read(DatagramPacketRecyclable packet) throws Throwable;
}
