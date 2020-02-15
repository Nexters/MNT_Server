package com.nexters.mnt.service;

import antlr.StringUtils;
import com.nexters.mnt.common.FirebaseUtil;
import com.nexters.mnt.controller.ApiStatus;
import com.nexters.mnt.domain.*;
import com.nexters.mnt.repository.ManittoRepository;
import com.nexters.mnt.repository.MissionRepository;
import com.nexters.mnt.repository.UserMissionRepository;
import com.nexters.mnt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MissionService {

    private UserMissionRepository userMissionRepository;

    private MissionRepository missionRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    FirebaseUtil firebaseUtil;

    public MissionService(UserMissionRepository userMissionRepository, MissionRepository missionRepository) {
        this.userMissionRepository = userMissionRepository;
        this.missionRepository = missionRepository;
    }

    @Transactional
    public ApiResponse<List<UserMission>> getTimeLine(Long roomId){
        return new ApiResponse<>(userMissionRepository.findByRoomIdAndUserDoneIsOrderByUserDoneTime(roomId, 1), ApiStatus.Ok);
    }

    @Transactional
    public ApiResponse<String> makeMission(Mission mission){
        List<Manitto> users = roomService.getUserList(mission.getRoomId()).getData();
        if(users == null){
            return new ApiResponse<>("", ApiStatus.DataNotFound);
        }
        List<UserMission> userMissionList  = new ArrayList<>();
        mission.setUserMissions(userMissionList);
        for(Manitto manitto : users){
            userMissionList.add(new UserMission(mission.getRoomId(), manitto.getUser(), mission));
        }
        missionRepository.save(mission);
        firebaseUtil.sendFcmToAll(userMissionList.stream()
        .map(UserMission::getUserId).map(User::getFcmToken).collect(Collectors.toList()),"마니또!", "테스트~!!" );
        return new ApiResponse<>("", ApiStatus.Ok);
    }

    public ApiResponse<List<Mission>> getMissionGroupBy(Long roomId){
        return new ApiResponse<>(missionRepository.findByRoomId(roomId), ApiStatus.Ok);
    }

    @Transactional
    public void sendMission(UserMission mission, Long missionId){
        userMissionRepository.updateUserMission(mission, missionId);
        User user = roomService.getMyManitto(mission.getUserId().getId(), mission.getRoomId()).getData();
        firebaseUtil.sendFcmUserToUser(user.getFcmToken(), "마니또!", "테스트~!!");
    }


}
