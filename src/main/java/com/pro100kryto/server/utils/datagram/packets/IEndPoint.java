package com.pro100kryto.server.utils.datagram.packets;

import java.net.InetAddress;

public interface IEndPoint {
    InetAddress getAddress();

    int getPort();
}
