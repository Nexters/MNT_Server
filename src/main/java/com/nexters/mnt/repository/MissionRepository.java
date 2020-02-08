package com.nexters.mnt.repository;

import com.nexters.mnt.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByRoomId (Long roomId);

}
