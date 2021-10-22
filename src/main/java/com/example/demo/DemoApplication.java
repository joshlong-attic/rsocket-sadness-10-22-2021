package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
public class DemoApplication {

    @MessageMapping("hello")
    String hello() {
        return "hello, world!";
    }

    @Bean
    ApplicationRunner runner() {
        return args -> {
            Thread.sleep(2_000);
            var requester = RSocketRequester.builder().tcp("localhost", 8181);
            var message = requester.route("hello").retrieveMono(String.class).block();
            System.out.println("message: " + message);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
