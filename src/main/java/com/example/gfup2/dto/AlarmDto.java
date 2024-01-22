package com.example.gfup2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.gfup.domain.model.Alarm;
import study.gfup.domain.model.DaysOfWeek;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDto {

    @Min(value=1)
    private Long id;

    private Long datetime;

    //@NotBlank
    private Boolean isRepeat;

    @Size(min=1, max=24, message = "이름 입력하셈")
    private String name;

    @Size(max=255, message="최대 길이 255")
    private String message;

    @NotNull
    private Alarm.Method method;

    @NotNull
    private Boolean isActive;

    @Size(max=7, message = "최대길이 7")
    private List<DaysOfWeek.DayOfWeek> day = new ArrayList<DaysOfWeek.DayOfWeek>();
}
