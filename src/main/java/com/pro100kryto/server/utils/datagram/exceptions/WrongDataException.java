package com.pro100kryto.server.utils.datagram.exceptions;

public class WrongDataException extends Exception{
    private final Code code;

    public WrongDataException(){
        this(Code.UNKNOWN);
    }

    public WrongDataException(Code code){
        super(code.toString());
        this.code = code;
    }

    public enum Code{
        UNKNOWN,
        WRONG_DATA,
        UNAUTHORIZED,
        NOT_ALLOWED
    }

    public Code getCode() {
        return code;
    }
}
