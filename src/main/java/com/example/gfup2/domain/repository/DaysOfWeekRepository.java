package com.example.gfup2.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.gfup2.domain.model.DaysOfWeek;

import java.util.Optional;

@Repository
public interface DaysOfWeekRepository extends JpaRepository<DaysOfWeek, Long> {
    Optional<DaysOfWeek> findByDay(DaysOfWeek.DayOfWeek day);
}
