package com.sdt.rpc.example.server;

import com.sdt.rpc.example.api.HelloService;
import com.sdt.rpc.server.RpcService;

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "hello " + name;
    }
}
