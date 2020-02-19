package com.nexters.mnt.service;

import com.nexters.mnt.common.FirebaseUtil;
import com.nexters.mnt.controller.ApiStatus;
import com.nexters.mnt.entity.*;
import com.nexters.mnt.repository.ManittoRepository;
import com.nexters.mnt.repository.MissionRepository;
import com.nexters.mnt.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MissionService {

    @Autowired
    private UserMissionRepository userMissionRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private ManittoRepository manittoRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    FirebaseUtil firebaseUtil;


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
//        firebaseUtil.sendFcmToAll(userMissionList.stream()
//        .map(UserMission::getUserId).map(User::getFcmToken).collect(Collectors.toList()),"마니또!", "테스트~!!" );
        return new ApiResponse<>("", ApiStatus.Ok);
    }

    public ApiResponse<List<Mission>> getMissionGroupBy(Long roomId){
        return new ApiResponse<>(missionRepository.findByRoomId(roomId), ApiStatus.Ok);
    }

    @Transactional
    public void sendMission(UserMission mission, Long missionId){
        userMissionRepository.updateUserMission(mission, missionId);
//        User user = roomService.getMyManitto(mission.getUserId().getId(), mission.getRoomId()).getData();
//        firebaseUtil.sendFcmUserToUser(user.getFcmToken(), "마니또!", "테스트~!!");
    }

    @Transactional
    public ApiResponse<List<UserMission>> getUserMission(String userId, Long roomId){
        return new ApiResponse<>(userMissionRepository.findByUserIdAndRoomId(userId, roomId),ApiStatus.Ok);
    }


    @Transactional
    public ApiResponse<List<UserMission>> getUserReceiveMission(String userId, Long roomId) {
        String manittoId = manittoRepository.findByManittoIdAndRoom(userId,roomId).get().getUser().getId();
        return new ApiResponse<>(userMissionRepository.findByUserIdAndRoomId(manittoId, roomId),ApiStatus.Ok);
    }
}
