package com.example.gfup2.domain.alarm.entity;

import com.example.gfup2.web.alarm.dto.AlarmRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private Long date;
    private String day;
    private Long time;
    private Integer repeatNum;
    private String name;
    private Integer method; // 0: 전화, 1: 메세지, 2: 이메일
    private String message;
    private Boolean isAlarmOn;

    public static Alarm from(AlarmRequest alarmRequest) {
        Alarm alarm = new Alarm();
        alarm.day = alarmRequest.getDay();
        alarm.date = alarmRequest.getDate();
        alarm.time = alarmRequest.getTime();
        alarm.repeatNum = alarmRequest.getRepeatNum();
        alarm.name = alarmRequest.getName();
        alarm.method = alarmRequest.getMethod();
        alarm.message = alarmRequest.getMessage();
        alarm.isAlarmOn = alarmRequest.getIsAlarmOn();

        return alarm;
    }

}
