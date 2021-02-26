package com.cursor.springdatamongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
public class SpringDataMongodbApplication {

    private final AlarmRepoImpl alarmRepo;

    @Autowired
    public SpringDataMongodbApplication(AlarmRepoImpl alarmRepo) {
        this.alarmRepo = alarmRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDataMongodbApplication.class, args);
    }

    @PostConstruct
    private void createOneAlarmForBegin() {
        alarmRepo.add(new Alarm(LocalTime.of(13, 20),
                List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)));
    }
}