package com.example.gfup2.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.gfup2.domain.model.AlarmToDaysOfWeek;

@Repository
public interface AlarmToDaysOfWeekRepository extends JpaRepository<AlarmToDaysOfWeek, Long> {
}
