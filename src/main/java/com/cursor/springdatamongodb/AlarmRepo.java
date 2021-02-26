package com.cursor.springdatamongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories(basePackages = "com.cursor.mongodb")
public interface AlarmRepo extends MongoRepository<Alarm, ObjectId> {
}
