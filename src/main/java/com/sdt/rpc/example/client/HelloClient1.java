package com.sdt.rpc.example.client;

import com.sdt.rpc.client.RpcProxy;
import com.sdt.rpc.example.api.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloClient1 {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_client.xml");
        RpcProxy proxy = context.getBean(RpcProxy.class);

        HelloService helloService = proxy.create(HelloService.class);
        int count = 0;
        while (true){
            String result = helloService.hello(String.valueOf(count++));
            System.out.println(result);
            Thread.sleep(1000);
        }

    }
}