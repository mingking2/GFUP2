package com.example.gfup2.domain.service;

import com.example.gfup2.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.gfup2.domain.model.Alarm;
import com.example.gfup2.domain.model.AlarmToDaysOfWeek;
import com.example.gfup2.domain.model.User;
import com.example.gfup2.domain.repository.AlarmRepository;
import com.example.gfup2.exception.EntityException;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.gfup2.util.Util.getSqlDateFromSeconds;
import static com.example.gfup2.util.Util.getSqlTimeFromSeconds;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepo;

    public Alarm create(
            Long dateTime,
            Boolean isRepeat,
            String name,
            String message,
            Alarm.Method method,
            Boolean isActive,
            List<AlarmToDaysOfWeek> day,
            User user
    ){
        Date date = getSqlDateFromSeconds(dateTime);
        Time time = getSqlTimeFromSeconds(dateTime);
        return this.alarmRepo.save(new Alarm(
                date,
                time,
                isRepeat,
                name,
                message,
                method,
                isActive,
                day,
                user
        ));
    }

    public Alarm read(User user, Long id) throws EntityException {
        Optional<Alarm> temp = this.alarmRepo.findByUser_IdAndId(user.getId(), id);
        if(temp.isEmpty()) throw new EntityException(Constants.RECORD_NOT_EXIST);
        return temp.get();
    }

    public List<Alarm> read(User user, Long id, int value){
        try{
            Alarm cur = this.read(user, id);
            return this.alarmRepo.findByTimeGreaterThenAndIdGreaterThanOrderByTimeAscIdAsc(user.getId(), cur.getTime(), id, value);
        }catch(EntityException ignore){
            return new ArrayList<>();
        }
    }

    public List<Alarm> read(User user,  int value) throws EntityException {
        return this.alarmRepo.findByTimeGreaterThenOrderByTimeAsc(user.getId(), value);
    }

    public void update(
            Long id,
            Long dateTime,
            Boolean isRepeat,
            String name,
            String message,
            Alarm.Method method,
            Boolean isActive,
            List<AlarmToDaysOfWeek> day,
            User user
    ) throws EntityException {
        Date date = getSqlDateFromSeconds(dateTime);
        Time time = getSqlTimeFromSeconds(dateTime);
        Alarm alarm = this.read(user, id);
        alarm.update(
                date,
                time,
                isRepeat,
                name,
                message,
                method,
                isActive,
                day
        );
        this.alarmRepo.save(alarm);
    }

    public void delete(User user, Long id) throws EntityException {
        this.alarmRepo.delete(this.read(user, id));
    }

}
