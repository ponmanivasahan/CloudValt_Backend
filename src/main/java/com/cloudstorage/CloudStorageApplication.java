package com.cloudstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class, args);
    }

    @org.springframework.context.annotation.Bean
    public org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy repairStrategy() {
        return flyway -> {
            flyway.repair();
            flyway.migrate();
        };
    }
}
