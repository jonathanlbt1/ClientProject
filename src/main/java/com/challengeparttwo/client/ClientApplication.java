package com.challengeparttwo.client;

import com.challengeparttwo.client.config.ClienteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.challengeparttwo.client")
public class ClientApplication implements CommandLineRunner {

    @Autowired
    private ClienteBuilder clienteBuilder;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        clienteBuilder.runOnStartUp();
    }
}
