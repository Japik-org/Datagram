package com.japik.utils.datagram.pool;

public class PoolEmptyException extends Exception{
    public static final PoolEmptyException instance = new PoolEmptyException();
}
