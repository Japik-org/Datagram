package com.japik.utils.datagram.packet;

public enum PacketStatus {
    /** ready for write new data */
    Ready,
    /** containing received data, ready to read */
    Received,
    /** recycled, does not use anymore */
    Recycled
}
