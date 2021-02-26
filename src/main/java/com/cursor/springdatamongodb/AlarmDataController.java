package com.cursor.springdatamongodb;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data")
public class AlarmDataController {

    private final AlarmRepoImpl alarmRepoService;

    @Autowired
    public AlarmDataController(AlarmRepoImpl alarmRepoService) {
        this.alarmRepoService = alarmRepoService;
    }

    @GetMapping
    public List<Alarm> getAllAlarms() {
        return alarmRepoService.getAll();
    }

    @GetMapping("/{id}")
    public Alarm getAlarmById(@PathVariable("id") String id) {
        return alarmRepoService.getById(id);
    }

    @PostMapping
    public Alarm createNewAlarm(@RequestBody AlarmRequest newAlarm) {
        return alarmRepoService.add(newAlarm.toAlarm());
    }

    @PutMapping("/time/{id}")
    public Alarm updateAlarmTime(@PathVariable("id") String id,
                                 @RequestBody TimeUpdateRequest updatedTime) {
        Alarm alarmToEdit = alarmRepoService.getById(id);
        alarmToEdit.setAlarmTime(updatedTime.toAlarm().getAlarmTime());
        return alarmRepoService.editAlarmTime(alarmToEdit);
    }

    @PutMapping("/schedule/{id}")
    public Alarm updateAlarmSchedule(@PathVariable("id") String id,
                                     @RequestBody ScheduleUpdateRequest updatedSchedule) {
        Alarm alarmToEdit = alarmRepoService.getById(id);
        alarmToEdit.setAlarmSchedule(updatedSchedule.toAlarm().getAlarmSchedule());
        return alarmRepoService.editAlarmSchedule(alarmToEdit);
    }

    @DeleteMapping("/{id}")
    public Alarm deleteAlarm(@PathVariable("id") String id) {
        return alarmRepoService.deleteById(id);
    }

    @Data
    private static class AlarmRequest {
        private int alarmHours;
        private int alarmMinutes;
        private String[] alarmDays;

        private Alarm toAlarm() {
            return new Alarm(LocalTime.of(alarmHours, alarmMinutes),
                    Arrays.stream(alarmDays).map(DayOfWeek::valueOf).collect(Collectors.toList()));
        }
    }

    @Data
    private static class TimeUpdateRequest {
        private int alarmHours;
        private int alarmMinutes;

        private Alarm toAlarm() {
            return new Alarm(LocalTime.of(alarmHours, alarmMinutes), null);
        }
    }

    @Data
    private static class ScheduleUpdateRequest {
        private String[] alarmDays;

        private Alarm toAlarm() {
            return new Alarm(null,
                    Arrays.stream(alarmDays).map(DayOfWeek::valueOf).collect(Collectors.toList()));
        }
    }
}
