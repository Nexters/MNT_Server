package com.nexters.mnt.repository;

import com.nexters.mnt.domain.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    List<UserMission> findByRoomIdOrderByUserDoneTime(Long roomId);
}
