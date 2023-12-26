package com.example.gfup2;

import com.example.gfup2.domain.alarm.entity.Alarm;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CronTest {

    public static void main(String[] args) {
        Alarm alarm = new Alarm();
        alarm.setDate(1702802494L); // 예시로 유닉스 시간을 설정

        generateCron(alarm);
    }

    private static void generateCron(Alarm alarm) {
        long date = alarm.getDate();

        // Unix 시간을 Date 객체로 변환 (날짜)
        Date dateObject = new Date(date * 1000);

        // Date 객체를 이용하여 크론 표현식 생성
        SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ?");
        String cronExpression = sdf.format(dateObject);

        System.out.println(cronExpression);
    }

}
