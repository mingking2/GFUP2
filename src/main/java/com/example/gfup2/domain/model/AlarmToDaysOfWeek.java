package com.example.gfup2.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class AlarmToDaysOfWeek {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Alarm alarm;

    @ManyToOne(fetch = FetchType.LAZY)
    private DaysOfWeek daysOfWeek;

    public AlarmToDaysOfWeek(DaysOfWeek daysOfWeek){
        this.daysOfWeek = daysOfWeek;
    }
}
