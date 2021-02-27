package com.cursor.springdatamongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepo extends MongoRepository<Alarm, ObjectId> {
}
