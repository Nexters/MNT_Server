package com.nexters.mnt.repository;

import com.nexters.mnt.domain.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    List<UserMission> findByRoomIdAndUserDoneIsOrderByUserDoneTime(Long roomId, int userDone);

    @Transactional
    @Modifying
    @Query("update UserMission u set u.userDone = 1, u.content = :#{#userMission.content}, u.missionImg = :#{#userMission.missionImg} " +
            "where u.roomId = :#{#userMission.roomId} and u.userId = :#{#userMission.userId} and u.missionId.id = :missionId")
    void updateUserMission(@Param("userMission") UserMission userMission, @Param("missionId") Long missionId);

    @Query("select u from UserMission u where u.roomId = :roomId and u.userId = :userId and u.missionId.id = :missionId")
    UserMission findByRoomIdAndUserIdAndMissionId(@Param("roomId") Long roomId, @Param("userId") String userId, @Param("missionId") Long missionId);
}
