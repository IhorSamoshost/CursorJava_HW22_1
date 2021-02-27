package com.cursor.springdatamongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.cursor.springdatamongodb")
public class MongoConfig {
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    @Bean
    public MongoDatabase mongoDatabase() {
        return mongoClient().getDatabase("my-db");
    }

    @Bean
    public MongoCollection<Document> mongoCollection() {
        return mongoDatabase().getCollection("data-alarms");
    }
}
