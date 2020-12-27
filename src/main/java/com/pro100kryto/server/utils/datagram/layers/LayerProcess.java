package com.pro100kryto.server.utils.datagram.layers;

public abstract class LayerProcess implements ILayerProcess{
    private final ILayerProcessCallback layerProcessCallback;

    public LayerProcess(ILayerProcessCallback layerProcessCallback) {
        this.layerProcessCallback = layerProcessCallback;
    }
}
