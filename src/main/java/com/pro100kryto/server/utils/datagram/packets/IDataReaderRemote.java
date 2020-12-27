package com.pro100kryto.server.utils.datagram.packets;

import java.rmi.Remote;

public interface IDataReaderRemote extends Remote {
    byte readByte();
}
