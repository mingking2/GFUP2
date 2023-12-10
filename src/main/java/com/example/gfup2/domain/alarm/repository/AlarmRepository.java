package com.example.gfup2.domain.alarm.repository;

import com.example.gfup2.domain.alarm.entity.Alarm;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}
