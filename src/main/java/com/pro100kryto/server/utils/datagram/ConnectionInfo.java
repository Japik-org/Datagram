package com.pro100kryto.server.utils.datagram;

public final class ConnectionInfo {
    public static final byte UNKNOWN = 0;
    public static final byte OK = 1;
    public static final byte WRONG_USER = 2;
    public static final byte WRONG_PASS = 3;
    public static final byte WRONG_CLIENT = 4;
    public static final byte OFFLINE = 5; // server offline
    public static final byte NO_RESPONSE = 6;
    public static final byte DB_ERROR = 7;
    public static final byte SERVER_ERROR = 8;
}