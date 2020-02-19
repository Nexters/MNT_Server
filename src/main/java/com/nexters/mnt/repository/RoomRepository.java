package com.nexters.mnt.repository;

import com.nexters.mnt.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Transactional
    @Modifying
    @Query(value = "update Room set isStart = 1 where id = :roomId")
    void updateStartRoom(@Param("roomId") Long roomId);

    Optional<Room> findByIdAndIsStartAndIsDone(Long id, int isStart, int isDone);

    @Transactional
    @Modifying
    @Query(value = "update room_tb set room_tb.is_start = 1 where date(room_tb.start_day) >= date(now()) ", nativeQuery = true)
    void updateStartRoomAuto();

    @Transactional
    @Modifying
    @Query(value = "update room_tb set room_tb.is_done = 1 where date(room_tb.end_day) <= date(now()) ", nativeQuery = true)
    void updateEndRoomAuto();

    @Transactional
    @Modifying
    @Query(value = "update room_tb set isDone = 1 where id = :roomId ", nativeQuery = true)
    void updateEndRoom(@Param("roomId")Long roomId );
}
