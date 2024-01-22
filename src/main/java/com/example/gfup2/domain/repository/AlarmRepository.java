package com.example.gfup2.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.gfup2.domain.model.Alarm;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Optional<Alarm> findByUser_IdAndId(Long userId, Long id);

    @Deprecated
    void deleteByUser_IdAndId(Long userId, Long id);

    @Deprecated
    @Query("SELECT a FROM Alarm a JOIN FETCH a.alarmToDaysOfWeek ad JOIN FETCH ad.daysOfWeek WHERE a.user.id = :userId AND a.id = :id")
    Optional<Alarm> findByUser_IdAndIdWithDaysOfWeek(@Param("userId") Long userId, @Param("id") Long id);
//    @Query("SELECT a FROM Alarm a WHERE a.time > :time OR (a.time = :time AND a.id > :id) ORDER BY a.time, a.id")
//    List<Alarm> findByTimeGreaterThenAndIdGreaterThanOrderByTimeAscIdAsc(@Param("time") Time time, @Param("id") Long id);
    @Query(value = "SELECT * FROM alarm WHERE user_id = :userId AND (`time` > :time OR (`time` = :time AND id >= :id)) ORDER BY `time`, id LIMIT :limit", nativeQuery = true)
    List<Alarm> findByTimeGreaterThenAndIdGreaterThanOrderByTimeAscIdAsc(@Param("userId") Long userId, @Param("time") Time time, @Param("id") Long id, @Param("limit") int limit);

    @Query(value = "SELECT * FROM alarm WHERE user_id = :userId ORDER BY `time`, id LIMIT :limit", nativeQuery = true)
    List<Alarm> findByTimeGreaterThenOrderByTimeAsc(@Param("userId") Long userId, @Param("limit") int limit);
}
