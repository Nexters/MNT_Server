package com.nexters.mnt.repository;

import com.nexters.mnt.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {


    List<Mission> findByRoomId (Long roomId);

    @Modifying
    @Query("delete from Mission where roomId = :roomId")
    void deleteMissionsByRoomId(@Param("roomId") Long roomId);
}
