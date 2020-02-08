package com.nexters.mnt.service;

import com.nexters.mnt.controller.ApiStatus;
import com.nexters.mnt.domain.*;
import com.nexters.mnt.repository.MissionRepository;
import com.nexters.mnt.repository.UserMissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MissionService {

    private UserMissionRepository userMissionRepository;

    private MissionRepository missionRepository;

    @Autowired
    private RoomService roomService;

    public MissionService(UserMissionRepository userMissionRepository, MissionRepository missionRepository) {
        this.userMissionRepository = userMissionRepository;
        this.missionRepository = missionRepository;
    }

    @Transactional
    public ApiResponse<List<UserMission>> getTimeLine(Long roomId){
        return new ApiResponse<>(userMissionRepository.findByRoomIdAndUserDoneIsOrderByUserDoneTime(roomId, 1), ApiStatus.Ok);
    }

    @Transactional
    public void makeMission(Mission mission){
        List<Manitto> users = roomService.getUserList(mission.getRoomId()).getData();
        if(users == null){
            return;
        }
        List<UserMission> userMissionList  = new ArrayList<>();
        mission.setUserMissions(userMissionList);
        for(Manitto manitto : users){
            userMissionList.add(new UserMission(mission.getRoomId(), manitto.getUser().getId(), mission));
        }
        missionRepository.save(mission);
    }

    public ApiResponse<List<Mission>> getMissionGroupBy(Long roomId){
        return new ApiResponse<>(missionRepository.findByRoomId(roomId), ApiStatus.Ok);
    }

    public void sendMission(UserMission mission, Long missionId){
        userMissionRepository.updateUserMission(mission, missionId);
    }


}
