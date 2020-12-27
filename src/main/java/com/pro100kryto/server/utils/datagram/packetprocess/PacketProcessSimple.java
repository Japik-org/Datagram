package com.pro100kryto.server.utils.datagram.packetprocess;


public abstract class PacketProcessSimple extends PacketProcessAbstract {
    public PacketProcessSimple(IPacketProcessCallback processCallback, int id) {
        super(processCallback, id);
    }

    @Override
    protected void startAction(){
        run();
    }

    @Override
    protected void stopAction(boolean force){
    }
}
