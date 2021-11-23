package com.pro100kryto.server.utils.datagram.packet;

public enum PacketStatus {
    /** ready for write new data */
    Ready,
    /** containing received data, ready to read */
    Received,
    /** final packet for send, ready to read */
    Final,
    /** recycled, does not use anymore */
    Recycled
}
