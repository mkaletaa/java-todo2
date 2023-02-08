package com.example.demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig {

    @Bean
    @Profile(value="prod")
    public MongoDatabase mongoDatabase(MongoClient mongoclient){
        return mongoclient.getDatabase("mydb");
    }

    @Bean
    @Profile(value="test")
    public MongoDatabase mongoDatabaseTest(MongoClient mongoclient){
        return mongoclient.getDatabase("mydbtest");
    }
}
