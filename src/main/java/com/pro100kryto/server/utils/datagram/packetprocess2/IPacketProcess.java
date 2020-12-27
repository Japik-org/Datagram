package com.pro100kryto.server.utils.datagram.packetprocess2;

import com.pro100kryto.server.utils.datagram.packets.IPacket;

public interface IPacketProcess {
    void run(IPacket packet);
}
