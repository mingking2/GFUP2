package com.example.gfup2.controller.alarm;

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
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final UserService userService;
    private final AlarmService alarmService;
    private final DaysOfWeekService daysOfWeekService;
    private final AlarmToDaysOfWeekService alarmToDaysOfWeekService;

    @PostMapping("/create")
    public ResponseEntity<?> alarmCreate(@RequestBody @Valid AlarmDto dt, BindingResult bindingResult){
        try{
            CheckError.checkValidException(bindingResult);

            Long dateTime = dt.getDatetime();
            Boolean isRepeat = dt.getIsRepeat();
            String name = dt.getName();
            String message = dt.getMessage();
            Alarm.Method method = dt.getMethod();
            Boolean isActive = dt.getIsActive();
            List<AlarmToDaysOfWeek> day = this.alarmToDaysOfWeekService
                    .getAndAddAlarmToDaysOfWeekByDaysOfWeek(
                            this.daysOfWeekService.dayOfWeekToDaysOfWeek(dt.getDay())
                    );
            User user = this.userService.getAuthUserFromSecurityContextHolder();

            return new ResponseEntity<AlarmIdDto>(
                    new AlarmIdDto(this.alarmService.create(
                            dateTime,
                            isRepeat,
                            name,
                            message,
                            method,
                            isActive,
                            day,
                            user
                    ).getId())
                    ,HttpStatus.OK
            );
        }catch(VerifyException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch(ValidException e){
            return new ResponseEntity<Map<String, String>>(e.getErrors(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read")
    public ResponseEntity<?> alarmRead(@RequestParam(value="id", required = false) Long id, @RequestParam(value = "value", required = false) Integer value){
        try{
            List<Alarm> alarms;
            if (value == null) value = 1;
            if (id == null){
                alarms = this.alarmService.read(this.userService.getAuthUserFromSecurityContextHolder(), value);
            }else{
                alarms = this.alarmService.read(this.userService.getAuthUserFromSecurityContextHolder(),id, value);
            }
            List<AlarmDto> alarmDtos = new ArrayList<AlarmDto>();

            for(Alarm alarm: alarms){
                Long alarmId = alarm.getId();
                Long dateTime = Util.getLocalSecondsFromSqlTimeAndDate(alarm.getDate(), alarm.getTime());
                Boolean isRepeat = alarm.getIsRepeat();
                String name = alarm.getName();
                String message = alarm.getMessage();
                Alarm.Method method = alarm.getMethod();
                Boolean isActive = alarm.getIsActive();
                List<DaysOfWeek.DayOfWeek> days = this.daysOfWeekService
                        .daysOfWeekToDayOfWeek(
                                this.alarmToDaysOfWeekService.getDaysOfWeekToAlarmToDaysOfWeek(
                                        alarm.getAlarmToDaysOfWeek()
                                )
                        );

                AlarmDto alarmDto = new AlarmDto(
                        alarmId,
                        dateTime,
                        isRepeat,
                        name,
                        message,
                        method,
                        isActive,
                        days
                );

                alarmDtos.add(alarmDto);
            }

            return new ResponseEntity<List<AlarmDto>>(alarmDtos, HttpStatus.OK);
        }catch(VerifyException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/put")
    public ResponseEntity<?> alarmUpdate(@RequestBody @Valid AlarmDto dt, BindingResult bindingResult){
        try{
            CheckError.checkValidException(bindingResult);

            Long id = dt.getId();
            Long dateTime = dt.getDatetime();
            Boolean isRepeat = dt.getIsRepeat();
            String name = dt.getName();
            String message = dt.getMessage();
            Alarm.Method method = dt.getMethod();
            Boolean isActive = dt.getIsActive();
            List<AlarmToDaysOfWeek> day = this.alarmToDaysOfWeekService
                    .getAndAddAlarmToDaysOfWeekByDaysOfWeek(
                            this.daysOfWeekService.dayOfWeekToDaysOfWeek(dt.getDay())
                    );
            User user = this.userService.getAuthUserFromSecurityContextHolder();

            this.alarmService.update(
                    id,
                    dateTime,
                    isRepeat,
                    name,
                    message,
                    method,
                    isActive,
                    day,
                    user
            );

            return new ResponseEntity<>(HttpStatus.OK);
        }catch(VerifyException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch(ValidException e){
            return new ResponseEntity<Map<String, String>>(e.getErrors(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> alarmDelete(@RequestBody @Valid AlarmIdDto dt, BindingResult bindingResult){
        try{
            CheckError.checkValidException(bindingResult);
            this.alarmService.delete(this.userService.getAuthUserFromSecurityContextHolder(), dt.getId());

            return new ResponseEntity<>(HttpStatus.OK);
        }catch(VerifyException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch(ValidException e){
            return new ResponseEntity<Map<String, String>>(e.getErrors(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
