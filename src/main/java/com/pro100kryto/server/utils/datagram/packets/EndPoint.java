package com.pro100kryto.server.utils.datagram.packets;

import java.net.InetAddress;

public final class EndPoint implements IEndPoint{
    private InetAddress address;
    private int port;

    public EndPoint(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public EndPoint(IEndPoint endPoint){
        this.address = endPoint.getAddress();
        this.port = endPoint.getPort();
    }

    @Override
    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean compare(IEndPoint endPoint2) {
        return endPoint2!=null && endPoint2.getAddress().equals(address);
    }

    public void setPort(int port) {
        this.port = port;
    }
}
