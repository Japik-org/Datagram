package com.pro100kryto.server.utils.datagram.packetprocess;


import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.utils.datagram.packets.IPacketInProcess;
import org.jetbrains.annotations.Nullable;

public interface IPacketProcessCallback {
    ILogger getLogger();
    void onPacketProcessFinished(int id);
    @Nullable
    IPacketInProcess getNewPacket();
    void sendPacket(IPacketInProcess packet);
}
