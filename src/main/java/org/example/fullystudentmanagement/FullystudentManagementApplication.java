package org.example.fullystudentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
@ComponentScan
@AutoConfigureAfter({DataSourceAutoConfiguration.class})


@SpringBootApplication
public class FullystudentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullystudentManagementApplication.class, args);
    }

}
