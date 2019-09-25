package com.beautyboss.slogen.easy.client;

public class RpcException extends RuntimeException {

    public RpcException(Exception e) {
        super(e);
    }

    public RpcException(String msg) {
        super(msg);
    }
}
