package com.example.gfup2.controller.alarm;

import com.example.gfup2.controller.ControllerBase;
import com.example.gfup2.exception.EntityException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.gfup2.domain.model.Alarm;
import com.example.gfup2.domain.model.AlarmToDaysOfWeek;
import com.example.gfup2.domain.model.DaysOfWeek;
import com.example.gfup2.domain.model.User;
import com.example.gfup2.domain.service.AlarmService;
import com.example.gfup2.domain.service.AlarmToDaysOfWeekService;
import com.example.gfup2.domain.service.DaysOfWeekService;
import com.example.gfup2.domain.service.UserService;
import com.example.gfup2.dto.AlarmDto;
import com.example.gfup2.dto.AlarmIdDto;
import com.example.gfup2.exception.CheckError;
import com.example.gfup2.exception.ValidException;
import com.example.gfup2.exception.VerifyException;
import com.example.gfup2.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final UserService userService;
    private final AlarmService alarmService;
    private final DaysOfWeekService daysOfWeekService;
    private final AlarmToDaysOfWeekService alarmToDaysOfWeekService;

    @PostMapping("/create")
    public ResponseEntity<?> alarmCreate(@RequestBody @Valid AlarmDto dt, BindingResult bindingResult){
        return AlarmControllerBase.run(() -> {
            CheckError.checkValidException(bindingResult);

            return new ResponseEntity<AlarmIdDto>(
                    new AlarmIdDto(this.alarmService.create(
                            dt.getDatetime(),
                            dt.getIsRepeat(),
                            dt.getName(),
                            dt.getMessage(),
                            dt.getMethod(),
                            dt.getIsActive(),
                            this.alarmToDaysOfWeekService
                                    .getAndAddAlarmToDaysOfWeekByDaysOfWeek(
                                            this.daysOfWeekService.dayOfWeekToDaysOfWeek(dt.getDay())
                                    ),
                            this.userService.getAuthUserFromSecurityContextHolder()
                    ).getId())
                    ,HttpStatus.OK
            );
        });
    }

    @GetMapping("/read")
    public ResponseEntity<?> alarmRead(@RequestParam(value="id", required = false) Long id, @RequestParam(value = "value", required = false) Integer value){
        return AlarmControllerBase.run(() -> {
            List<Alarm> alarms;
            Integer v = value;
            if (v == null) v = 1;
            if (id == null){
                alarms = this.alarmService.read(this.userService.getAuthUserFromSecurityContextHolder(), v);
            }else{
                alarms = this.alarmService.read(this.userService.getAuthUserFromSecurityContextHolder(),id, v);
            }
            List<AlarmDto> alarmDtos = new ArrayList<AlarmDto>();

            for(Alarm alarm: alarms){
                AlarmDto alarmDto = new AlarmDto(
                        alarm.getId(),
                        Util.getLocalSecondsFromSqlTimeAndDate(alarm.getDate(), alarm.getTime()),
                        alarm.getIsRepeat(),
                        alarm.getName(),
                        alarm.getMessage(),
                        alarm.getMethod(),
                        alarm.getIsActive(),
                        this.daysOfWeekService
                                .daysOfWeekToDayOfWeek(
                                        this.alarmToDaysOfWeekService.getDaysOfWeekToAlarmToDaysOfWeek(
                                                alarm.getAlarmToDaysOfWeek()
                                        )
                                )
                );
                alarmDtos.add(alarmDto);
            }

            return new ResponseEntity<List<AlarmDto>>(alarmDtos, HttpStatus.OK);
        });
    }

    @PutMapping("/put")
    public ResponseEntity<?> alarmUpdate(@RequestBody @Valid AlarmDto dt, BindingResult bindingResult){
        return AlarmControllerBase.run(() -> {
            CheckError.checkValidException(bindingResult);

            this.alarmService.update(
                    dt.getId(),
                    dt.getDatetime(),
                    dt.getIsRepeat(),
                    dt.getName(),
                    dt.getMessage(),
                    dt.getMethod(),
                    dt.getIsActive(),
                    this.alarmToDaysOfWeekService
                            .getAndAddAlarmToDaysOfWeekByDaysOfWeek(
                                    this.daysOfWeekService.dayOfWeekToDaysOfWeek(dt.getDay())
                            ),
                    this.userService.getAuthUserFromSecurityContextHolder()
            );

            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> alarmDelete(@RequestBody @Valid AlarmIdDto dt, BindingResult bindingResult){
        return AlarmControllerBase.run(() -> {
                CheckError.checkValidException(bindingResult);
                this.alarmService.delete(this.userService.getAuthUserFromSecurityContextHolder(), dt.getId());
                return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    private static class AlarmControllerBase {
        public static ResponseEntity<?> run(ControllerBase base){
            try{
                return base.run();
            }catch(VerifyException e){
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
            }catch(ValidException e){
                return new ResponseEntity<Map<String, String>>(e.getErrors(), HttpStatus.BAD_REQUEST);
            }catch(Exception e){
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

    }

}
