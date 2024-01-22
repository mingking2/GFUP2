package com.example.gfup2.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.gfup2.domain.model.AlarmToDaysOfWeek;
import com.example.gfup2.domain.model.DaysOfWeek;
import com.example.gfup2.domain.repository.AlarmToDaysOfWeekRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmToDaysOfWeekService {
    private final AlarmToDaysOfWeekRepository AlarmToDaysOfWeekRepo;

    public List<AlarmToDaysOfWeek> getAndAddAlarmToDaysOfWeekByDaysOfWeek(List<DaysOfWeek> days){
        List<AlarmToDaysOfWeek> alarmToDaysOfWeeks = new ArrayList<AlarmToDaysOfWeek>();
        for(DaysOfWeek d: days){
            alarmToDaysOfWeeks.add(new AlarmToDaysOfWeek(d));
        }
        return alarmToDaysOfWeeks;
    }

    public List<DaysOfWeek> getDaysOfWeekToAlarmToDaysOfWeek(List<AlarmToDaysOfWeek> alarmToDaysOfWeeks){
        List<DaysOfWeek> days = new ArrayList<DaysOfWeek>();
        for(AlarmToDaysOfWeek d: alarmToDaysOfWeeks){
            days.add(d.getDaysOfWeek());
        }
        return days;
    }
}
