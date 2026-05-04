package com.pm.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserManagementApplication {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserManagementApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(UserManagementApplication.class, args);
        System.out.println("UserManagementApplication started");
        logger.info("UserManagementApplication started successfully on Docker!");
    }

}
