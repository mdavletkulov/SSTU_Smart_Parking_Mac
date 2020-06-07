package com.example.Poller;

import com.example.Poller.service.PollerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class Application {

    @Autowired
    PollerService pollerService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() throws InterruptedException, IOException {
         while (true) {
             pollerService.processSnapshot();
             Thread.sleep(20000);
         }
    }
}