package com.example.gfup2.web.alarm.service;

import com.example.gfup2.domain.alarm.entity.Alarm;
import com.example.gfup2.domain.alarm.repository.AlarmRepository;
import com.example.gfup2.web.alarm.dto.AlarmRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private TaskScheduler taskScheduler;

    public Alarm createAlarm(AlarmRequest requestDTO) {
        // AlarmRequestDTO를 기반으로 알람 생성 로직을 작성
        Alarm alarm = Alarm.from(requestDTO);

        // DB에 알람 저장
        Alarm savedAlarm = alarmRepository.save(alarm);

        // 생성된 알람에 대한 크론 표현식 생성
        String cronExpression = generateCron(savedAlarm);
        System.out.println(cronExpression);

        // 스케줄러에 크론 표현식을 적용하여 알람 실행
        taskScheduler.schedule(() -> workAlarm(savedAlarm.getId()), new CronTrigger(cronExpression));

        return savedAlarm;
    }

    public void workAlarm(Long alarmId) {
        System.out.println("알람 동작 - 알람 ID: " + alarmId);
    }

    private String generateCron(Alarm alarm) {
        long date = alarm.getDate();

        // Unix 시간을 Date 객체로 변환 (날짜)
        Date dateObject = new Date(date * 1000L);

        // Date 객체를 이용하여 크론 표현식 생성
        SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ?");
        String cronExpression = sdf.format(dateObject);

        return cronExpression;
    }


}
