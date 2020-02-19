package com.nexters.mnt.repository;

import com.nexters.mnt.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    @Query("select u from UserMission u join fetch u.missionId where u.roomId = :roomId and u.userDone = :userDone order by u.userDoneTime")
    List<UserMission> findByRoomIdAndUserDoneIsOrderByUserDoneTime(@Param(value = "roomId") Long roomId, @Param(value = "userDone") int userDone);

    @Transactional
    @Modifying
    @Query("update UserMission u set u.userDone = 1, u.content = :#{#userMission.content}, u.missionImg = :#{#userMission.missionImg} " +
            "where u.roomId = :#{#userMission.roomId} and u.userId = :#{#userMission.userId} and u.missionId.id = :missionId")
    void updateUserMission(@Param("userMission") UserMission userMission, @Param("missionId") Long missionId);

    @Query("select u from UserMission u where u.roomId = :roomId and u.userId = :userId and u.missionId.id = :missionId")
    UserMission findByRoomIdAndUserIdAndMissionId(@Param("roomId") Long roomId, @Param("userId") String userId, @Param("missionId") Long missionId);

    @Query("select u from UserMission u where u.userId.id = :userId and u.roomId = :roomId")
    List<UserMission> findByUserIdAndRoomId(@Param("userId")String userId, @Param("roomId")Long roomId);

    @Query("delete from UserMission u where u.userId.id = :userId and u.roomId = :roomId")
    void deleteByUserIdAndRoomId(@Param("userId")String userId, @Param("roomId")Long roomId);

    @Modifying
    @Query("delete from UserMission where roomId = :roomId")
    void deleteUserMissionsByRoomId(@Param("roomId") Long roomId);
}
