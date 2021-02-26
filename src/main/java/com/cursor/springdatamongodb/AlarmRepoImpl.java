package com.cursor.springdatamongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmRepoImpl {
    @Autowired
    private AlarmRepo alarmRepo;

    public Alarm add(Alarm alarmParam) {
        return alarmRepo.save(alarmParam);
    }

    public Alarm editAlarmTime(Alarm alarmParam) {
        Alarm alarmToEdit = alarmRepo.findById(alarmParam.getAlarmId()).orElse(null);
        assert alarmToEdit != null;
        alarmToEdit.setAlarmTime(alarmParam.getAlarmTime());
        return alarmRepo.save(alarmToEdit);
    }

    public Alarm editAlarmSchedule(Alarm alarmParam) {
        Alarm alarmToEdit = alarmRepo.findById(alarmParam.getAlarmId()).orElse(null);
        assert alarmToEdit != null;
        alarmToEdit.setAlarmSchedule(alarmParam.getAlarmSchedule());
        return alarmRepo.save(alarmToEdit);
    }

    public Alarm deleteById(String id) {
        alarmRepo.deleteById(new ObjectId(id));
        return alarmRepo.findById(new ObjectId(id)).orElse(null);
    }

    public Alarm getById(String id) {
        return alarmRepo.findById(new ObjectId(id)).orElse(null);
    }

    public List<Alarm> getAll() {
        MongoClient client = MongoClients.create();
        MongoDatabase myDb = client.getDatabase("my-db");
        MongoCollection<Document> myAlarms = myDb.getCollection("my-alarms");
        return alarmRepo.findAll();
    }
}

