package com.nexters.mnt.controller;

import com.nexters.mnt.entity.ApiResponse;
import com.nexters.mnt.entity.Mission;
import com.nexters.mnt.entity.UserMission;
import com.nexters.mnt.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mission")
public class MissionController {

    @Autowired
    MissionService missionService;

    @RequestMapping(value = "/list/{roomId}", method = RequestMethod.GET)
    public ApiResponse<List<UserMission>> getTimeLine(@PathVariable Long roomId){
        return missionService.getTimeLine(roomId);
    }

    @RequestMapping(value = "/list/order-mission/{roomId}", method = RequestMethod.GET)
    public ApiResponse<List<Mission>> getMissionGroupBy(@PathVariable Long roomId){
        return missionService.getMissionGroupBy(roomId);
    }

    @RequestMapping(value = "/make", method = RequestMethod.POST)
    public ApiResponse<String> makeMission(@RequestBody Mission mission){
        return missionService.makeMission(mission);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void sendMission(@RequestBody UserMission userMission, @RequestParam Long missionId){
        missionService.sendMission(userMission, missionId);
    }

    @RequestMapping(value = "/done/{userId}", method = RequestMethod.GET)
    public ApiResponse<List<UserMission>> getUserMission(@PathVariable String userId, @RequestParam Long roomId){
        return missionService.getUserMission(userId, roomId);
    }

    @RequestMapping(value = "/receive/{userId}", method = RequestMethod.GET)
    public ApiResponse<List<UserMission>> getUserReceiveMission(@PathVariable String userId, @RequestParam Long roomId){
        return missionService.getUserReceiveMission(userId, roomId);
    }
}
