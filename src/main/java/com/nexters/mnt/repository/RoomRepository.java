package com.nexters.mnt.repository;

import com.nexters.mnt.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Transactional
    @Modifying
    @Query("update Room set isStart = 1 where id = :roomId")
    void updateStartRoom(@Param("roomId") Long roomId);

    Optional<Room> findByIdAndIsStartAndIsDone(Long id, int isStart, int isDone);
}
