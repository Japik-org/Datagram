package com.pro100kryto.server.utils.datagram.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.InetAddress;

@Getter
@AllArgsConstructor
public final class EndPoint{
    private final InetAddress address;
    private final int port;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!obj.getClass().equals(EndPoint.class)) return false;

        final EndPoint endPoint2 = (EndPoint) obj;
        return endPoint2.address.equals(address) && endPoint2.port == port;
    }
}
