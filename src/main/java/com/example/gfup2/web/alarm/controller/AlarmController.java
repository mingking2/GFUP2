package com.example.gfup2.web.alarm.controller;

import com.example.gfup2.domain.alarm.entity.Alarm;
import com.example.gfup2.web.alarm.dto.AlarmRequest;
import com.example.gfup2.web.alarm.service.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Alarm 컨트롤러", description = "ALARM API입니다.")
@RestController
@RequestMapping("/alarm")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @Operation(summary = "알람생성", description = "알람을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "알람 생성 성공"),
            @ApiResponse(responseCode = "401", description = "인가 기능이 확인되지 않은 접근"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })

    @PostMapping("/create")
    public ResponseEntity<Alarm> createAlarm(@RequestBody AlarmRequest request) {
        return ResponseEntity.ok(alarmService.createAlarm(request));
    }

}
