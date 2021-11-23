package com.pro100kryto.server.utils.datagram.pool;

public class PoolEmptyException extends Exception{
    public static final PoolEmptyException instance = new PoolEmptyException();
}
