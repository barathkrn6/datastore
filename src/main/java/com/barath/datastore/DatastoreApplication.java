package com.barath.datastore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@Slf4j
public class DatastoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatastoreApplication.class, args);
    }

}
