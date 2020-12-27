package com.pro100kryto.server.utils.datagram.layers;

import java.net.DatagramPacket;

public interface ILayerProcessCallback {
    void sendPacket(DatagramPacket packet);
}
