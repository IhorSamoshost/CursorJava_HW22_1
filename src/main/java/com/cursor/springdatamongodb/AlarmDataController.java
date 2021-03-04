package com.cursor.springdatamongodb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public AlarmDataController(AlarmRepoImpl alarmRepoService) {
        this.alarmRepoService = alarmRepoService;
    }

    @GetMapping
    public List<String> getAllAlarms() {
        return alarmRepoService.getAll().stream()
                .map(a -> {
                    try {
                        return mapper.writeValueAsString(a);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return a.toString();
                    }
                }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public String getAlarmById(@PathVariable("id") String id) {
        try {
            return mapper.writeValueAsString(alarmRepoService.getById(id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return alarmRepoService.getById(id).toString();
        }
    }

    @PostMapping
    public String createNewAlarm(@RequestBody AlarmRequest newAlarm) {
        return alarmRepoService.add(newAlarm.toAlarm()).toString();
    }

    @PutMapping("/time/{id}")
    public String updateAlarmTime(@PathVariable("id") String id,
                                  @RequestBody TimeUpdateRequest updatedTime) {
        Alarm alarmToEdit = alarmRepoService.getById(id);
        alarmToEdit.setAlarmTime(updatedTime.toAlarm().getAlarmTime());
        return alarmRepoService.editAlarmTime(alarmToEdit).toString();
    }

    @PutMapping("/schedule/{id}")
    public String updateAlarmSchedule(@PathVariable("id") String id,
                                      @RequestBody ScheduleUpdateRequest updatedSchedule) {
        Alarm alarmToEdit = alarmRepoService.getById(id);
        alarmToEdit.setAlarmSchedule(updatedSchedule.toAlarm().getAlarmSchedule());
        return alarmRepoService.editAlarmSchedule(alarmToEdit).toString();
    }

    @DeleteMapping("/{id}")
    public String deleteAlarm(@PathVariable("id") String id) {
        Alarm deletedAlarm = alarmRepoService.deleteById(id);
        return deletedAlarm != null ? deletedAlarm.toString() : "Alarm is successfully deleted!";
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
