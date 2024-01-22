package com.example.gfup2.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.gfup2.domain.model.DaysOfWeek;
import com.example.gfup2.domain.repository.DaysOfWeekRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DaysOfWeekService {
    private final DaysOfWeekRepository daysOfWeekRepo;

    public void create(DaysOfWeek.DayOfWeek day){
        daysOfWeekRepo.save(new DaysOfWeek(day));
    }

    public DaysOfWeek read(DaysOfWeek.DayOfWeek day){
        Optional<DaysOfWeek> temp = daysOfWeekRepo.findByDay(day);
        return temp.orElse(null);
    }

    public List<DaysOfWeek> dayOfWeekToDaysOfWeek(List<DaysOfWeek.DayOfWeek> days){
        List<DaysOfWeek> day = new ArrayList<DaysOfWeek>();
        for(DaysOfWeek.DayOfWeek d : days){
            DaysOfWeek temp = this.read(d);
            if(temp == null) {
                this.create(d);
                temp = this.read(d);
            }
            day.add(temp);
        }
        return day;
    }

    //개헷갈리네 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
    public List<DaysOfWeek.DayOfWeek> daysOfWeekToDayOfWeek(List<DaysOfWeek> day){
        return day.stream().map(DaysOfWeek::getDay).toList();
    }
}
