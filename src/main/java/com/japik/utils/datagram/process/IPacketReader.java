package com.japik.utils.datagram.process;


import com.japik.utils.datagram.packet.DatagramPacketRecyclable;

public interface IPacketReader {
    void read(DatagramPacketRecyclable packet) throws Throwable;
}
