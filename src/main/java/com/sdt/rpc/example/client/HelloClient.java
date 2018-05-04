package com.sdt.rpc.example.client;

import com.sdt.rpc.client.RpcProxy;
import com.sdt.rpc.example.api.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloClient {
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_client.xml");
        RpcProxy proxy = context.getBean(RpcProxy.class);

        HelloService helloService = proxy.create(HelloService.class);

        String result = helloService.hello("World");
        System.out.println(result);
    }
}