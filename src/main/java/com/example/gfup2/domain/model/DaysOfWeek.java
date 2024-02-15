package com.example.gfup2.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class DaysOfWeek {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private DayOfWeek day;

    @OneToMany(mappedBy = "daysOfWeek")
    private List<AlarmToDaysOfWeek> alarmToDaysOfWeek = new ArrayList<AlarmToDaysOfWeek>();

    public DaysOfWeek(DayOfWeek day){
        this.day = day;
    }
    public enum DayOfWeek{
        SUN, MON, TUE, WED, THU, FRI, SAT
    }
}
