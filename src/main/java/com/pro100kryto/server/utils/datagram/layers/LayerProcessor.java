package com.pro100kryto.server.utils.datagram.layers;

import com.pro100kryto.server.utils.datagram.exceptions.NoActionException;
import com.pro100kryto.server.utils.datagram.exceptions.WrongDataException;
import com.pro100kryto.server.utils.datagram.packets.IPacket;
import com.pro100kryto.server.utils.datagram.packets.IPacketInProcess;

import java.util.ArrayList;
import java.util.List;

public class LayerProcessor {
    private final List<ILayerProcess> layerProcessList;

    public LayerProcessor(){
        layerProcessList = new ArrayList<>();
    }

    public IPacket process(IPacketInProcess packet) throws WrongDataException, NoActionException {
        for (ILayerProcess layer : layerProcessList){
            layer.process(packet);
        }
        return packet.convertToFinalPacket();
    }

    public void addLayer(ILayerProcess layer){
        layerProcessList.add(layer);
    }

    public void removeLayers(){
        for (ILayerProcess layer : layerProcessList){
            layer.destroy();
        }
        layerProcessList.clear();
    }
}
