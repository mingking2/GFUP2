package com.example.gfup2.web.alarm.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmRequest {
    private Long date;
    private String day;
    private Long time;
    private Integer repeatNum;
    private String name;
    private Integer method;
    private String message;
    private Boolean isAlarmOn;
}
