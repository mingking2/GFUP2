package com.example.gfup2.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED, force= true)
@Table(
        indexes = @Index(name="idxAlarmTime", columnList = "time, id")
)
public class Alarm {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true, name="`date`")
    private Date date; //일단은 날짜로 저장했는데.. 초로 저장해야 할지..

    @Temporal(TemporalType.TIME)
    @Column(nullable = false, name="`time`")
    private Time time;

    @Column(nullable = true)
    private Boolean isRepeat;

    @Column(nullable = false, length=24)
    private String name;

    @Column(nullable = true, length=255)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length=10)
    private Method method;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy="alarm", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<AlarmToDaysOfWeek> alarmToDaysOfWeek = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Alarm(
            Date date,
            Time time,
            Boolean isRepeat,
            String name,
            String message,
            Method method,
            Boolean isActive,
            List<AlarmToDaysOfWeek> alarmToDaysOfWeek,
            User user
    ){
        this.date = date;
        this.time = time;
        this.isRepeat = isRepeat;
        this.name = name;
        this.message = message;
        this.method = method;
        this.isActive = isActive;
        this.user = user;
        this.updateAlarmToDaysOfWeek(alarmToDaysOfWeek);
    }

    public void update(
            Date date,
            Time time,
            Boolean isRepeat,
            String name,
            String message,
            Method method,
            Boolean isActive,
            List<AlarmToDaysOfWeek> alarmToDaysOfWeek
    ){
        this.date = date;
        this.time = time;
        this.isRepeat = isRepeat;
        this.name = name;
        this.message = message;
        this.method = method;
        this.isActive = isActive;
        this.updateAlarmToDaysOfWeek(alarmToDaysOfWeek);
    }

    private void updateAlarmToDaysOfWeek(List<AlarmToDaysOfWeek> alarmToDaysOfWeeks){
        for(AlarmToDaysOfWeek day : alarmToDaysOfWeeks){
            day.setAlarm(this);
            this.alarmToDaysOfWeek.add(day);
        }
    }

    public enum Method{
        CALL, EMAIL, SMS
    }
}
