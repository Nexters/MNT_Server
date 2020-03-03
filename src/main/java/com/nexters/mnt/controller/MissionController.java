package com.nexters.mnt.controller;

import com.nexters.mnt.entity.ApiResponse;
import com.nexters.mnt.entity.Mission;
import com.nexters.mnt.entity.UserMission;
import com.nexters.mnt.entity.dto.MissionRequest;
import com.nexters.mnt.entity.dto.MissionResponse;
import com.nexters.mnt.entity.dto.UserMissionRequest;
import com.nexters.mnt.entity.dto.UserMissionResponse;
import com.nexters.mnt.service.MissionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/mission")
public class MissionController {

    @Autowired
    MissionService missionService;


    @RequestMapping(value = "/list/{roomId}", method = RequestMethod.GET)
    public ApiResponse<List<UserMissionResponse>> getTimeLine(@PathVariable Long roomId){
        return missionService.getTimeLine(roomId);
    }

    @RequestMapping(value = "/list/order-mission/{roomId}", method = RequestMethod.GET)
    public ApiResponse<List<MissionResponse>> getMissionGroupBy(@PathVariable Long roomId){
        return missionService.getMissionGroupBy(roomId);
    }

    @RequestMapping(value = "/make", method = RequestMethod.POST)
    public ApiResponse<String> makeMission(@RequestBody MissionRequest mission){
        return missionService.makeMission(mission);
    }

    @ApiOperation(value = "미션 수행", produces = "multipart/form-data")
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = MediaType.MULTIPART_FORM_DATA_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void sendMission(@ModelAttribute("userMissionModel") UserMissionRequest userMission) {
        missionService.sendMission(userMission);
    }

    @RequestMapping(value = "/done/{userId}", method = RequestMethod.GET)
    public ApiResponse<List<UserMissionResponse>> getUserMission(@PathVariable String userId, @RequestParam Long roomId){
        return missionService.getUserMission(userId, roomId);
    }

    @RequestMapping(value = "/receive/{userId}", method = RequestMethod.GET)
    public ApiResponse<List<UserMissionResponse>> getUserReceiveMission(@PathVariable String userId, @RequestParam Long roomId){
        return missionService.getUserReceiveMission(userId, roomId);
    }
}
