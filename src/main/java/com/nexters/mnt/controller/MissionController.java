package com.nexters.mnt.controller;

import com.nexters.mnt.domain.ApiResponse;
import com.nexters.mnt.domain.Mission;
import com.nexters.mnt.domain.UserMission;
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

}
