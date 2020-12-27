package com.pro100kryto.server.utils.datagram.layers;

import com.pro100kryto.server.utils.datagram.exceptions.NoActionException;
import com.pro100kryto.server.utils.datagram.exceptions.WrongDataException;
import com.pro100kryto.server.utils.datagram.packets.IPacketInProcess;

public interface ILayerProcess {
    void process(IPacketInProcess packet) throws WrongDataException, NoActionException;
    void update();
    void reset();
    void destroy();
}
