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

    @RequestMapping(value = "/mission/list/order-mission/{roomId}", method = RequestMethod.GET)
    public ApiResponse<List<Mission>> getMissionGroupBy(@PathVariable Long roomId){
        return missionService.getMissionGroupBy(roomId);
    }

    @RequestMapping(value = "/mission/make", method = RequestMethod.POST)
    public void makeMission(@RequestBody Mission mission){
        missionService.makeMission(mission);
    }

    @RequestMapping(value = "/mission/send", method = RequestMethod.POST)
    public void sendMission(@RequestBody UserMission userMission, @RequestParam Long missionId){
        missionService.sendMission(userMission, missionId);
    }

}
