package com.cursor.springdatamongodb;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "my-alarms")
public class Alarm implements Serializable {
    @Id
    @JsonFormat(shape = JsonFormat.Shape.NATURAL)
    private ObjectId alarmId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime alarmTime;
    private List<DayOfWeek> alarmSchedule;

    public Alarm(LocalTime alarmTime, List<DayOfWeek> alarmSchedule) {
        this.alarmTime = alarmTime;
        this.alarmSchedule = alarmSchedule;
    }
}
