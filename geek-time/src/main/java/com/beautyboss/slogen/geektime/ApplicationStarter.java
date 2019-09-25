package com.beautyboss.slogen.geektime;

import com.beautyboss.slogen.easy.client.EnableEasyClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEasyClients(basePackages = "com.beautyboss.slogen")
public class ApplicationStarter {

    public static void main(String[] args) {
        new SpringApplication(ApplicationStarter.class).run(args);
    }
}
