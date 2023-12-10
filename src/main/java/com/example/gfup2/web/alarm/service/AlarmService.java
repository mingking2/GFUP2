package com.example.gfup2.web.alarm.service;

import com.example.gfup2.domain.alarm.entity.Alarm;
import com.example.gfup2.domain.alarm.repository.AlarmRepository;
import com.example.gfup2.web.alarm.dto.AlarmRequest;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    public Alarm createAlarm(AlarmRequest requestDTO) {
        // AlarmRequestDTO를 기반으로 알람 생성 로직을 작성

        Alarm alarm = Alarm.from(requestDTO);
        // DB에 알람 저장
        return alarmRepository.save(alarm);

    }


}
