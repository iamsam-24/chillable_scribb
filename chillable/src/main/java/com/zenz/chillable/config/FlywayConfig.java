package com.zenz.chillable.config;

import org.springframework.boot.flyway.autoconfigure.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean
   public FlywayMigrationStrategy flywayMigrationStrategy(){

        return flyway -> {
            flyway.migrate();
            flyway.repair();
        };
    }

}
