package com.sdt.rpc.example.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class BootStrap1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(BootStrap.class);

    public static void main(String[] args){
        LOGGER.debug("start server");

        new ClassPathXmlApplicationContext("spring_server_1.xml");
    }
}
