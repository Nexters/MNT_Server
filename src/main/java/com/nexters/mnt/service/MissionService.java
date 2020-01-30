package com.nexters.mnt.service;

import com.nexters.mnt.domain.UserMission;
import com.nexters.mnt.repository.MissionRepository;
import com.nexters.mnt.repository.UserMissionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MissionService {

    private UserMissionRepository userMissionRepository;

    private MissionRepository missionRepository;

    public MissionService(UserMissionRepository userMissionRepository, MissionRepository missionRepository) {
        this.userMissionRepository = userMissionRepository;
        this.missionRepository = missionRepository;
    }

    @Transactional
    public List<UserMission> getTimeLine(Long roomId){
        return userMissionRepository.findByRoomIdOrderByUserDoneTime(roomId);
    }


}
