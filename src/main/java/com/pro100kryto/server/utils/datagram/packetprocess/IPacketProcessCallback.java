package com.pro100kryto.server.utils.datagram.packetprocess;


import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.utils.datagram.packets.IPacketInProcess;
import com.sun.istack.internal.Nullable;

public interface IPacketProcessCallback {
    ILogger getLogger();
    void onPacketProcessFinished(int id);
    @Nullable
    IPacketInProcess getNewPacket();
    void sendPacket(IPacketInProcess packet);
}
