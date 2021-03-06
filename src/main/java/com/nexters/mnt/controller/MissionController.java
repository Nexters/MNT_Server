package com.nexters.mnt.controller;

import com.nexters.mnt.entity.ApiResponse;
import com.nexters.mnt.entity.Mission;
import com.nexters.mnt.entity.UserMission;
import com.nexters.mnt.entity.dto.MissionRequest;
import com.nexters.mnt.entity.dto.MissionResponse;
import com.nexters.mnt.entity.dto.UserMissionRequest;
import com.nexters.mnt.entity.dto.UserMissionResponse;
import com.nexters.mnt.service.MissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/mission")
public class MissionController {

    @Autowired
    MissionService missionService;

    @ApiOperation(value = "미션 목록", notes = "사용자들이 수행한 미션을 시간순으로 보여줍니다.", response = UserMissionResponse.class)
    @RequestMapping(value = "/list/{roomId}", method = RequestMethod.GET)
    public ApiResponse<List<UserMissionResponse>> getTimeLine(@PathVariable Long roomId){
        return missionService.getTimeLine(roomId);
    }

    @ApiOperation(value = "미션별로 정렬", notes = "미션 마다 사용자들의 미션 수행여부와 결과를 보여줍니다.")
    @RequestMapping(value = "/list/order-mission/{roomId}", method = RequestMethod.GET)
    public ApiResponse<List<MissionResponse>> getMissionGroupBy(@PathVariable Long roomId){
        return missionService.getMissionGroupBy(roomId);
    }

    @ApiOperation(value = "미션 만들기", notes = "관리자가 미션을 만들때 사용합니다.")
    @RequestMapping(value = "/make", method = RequestMethod.POST)
    public ApiResponse<String> makeMission(@RequestBody MissionRequest mission){
        return missionService.makeMission(mission);
    }

    @ApiOperation(value = "미션 수행", produces = "multipart/form-data", notes = "미션 수행을 위한 값을 넘겨줍니다")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ApiResponse<String> sendMission( @RequestParam Long roomId,
            @RequestParam String userId,
            @RequestParam Long missionId,
            @RequestParam(required = false) String content,
            @RequestPart(required = false) MultipartFile img) {
        UserMissionRequest userMission = new UserMissionRequest(roomId, userId, missionId, content,new Date(), img);
        return missionService.sendMission(userMission);
    }

    @ApiOperation(value = "미션 상세보기", notes = "사용자들이 수행한 미션 상세보기", response = UserMissionResponse.class)
    @RequestMapping(value = "/{userMissionId}", method = RequestMethod.GET)
    public ApiResponse<UserMissionResponse> getUserMission(@PathVariable Long userMissionId){
        return missionService.getUserMission(userMissionId);
    }

    @ApiOperation(value = "사용자의 미션 현황", notes = "사용자가 미션에 대한 수행 유무를 보여줍니다.")
    @RequestMapping(value = "/done/{userId}", method = RequestMethod.GET)
    public ApiResponse<List<UserMissionResponse>> getUserMissionList(@PathVariable String userId, @RequestParam Long roomId){
        return missionService.getUserMission(userId, roomId);
    }

    @ApiOperation(value = "사용자가 받은 미션", notes = "사용자가 나의 프로또로 부터 받은 미션을 보여줍니다.")
    @RequestMapping(value = "/receive/{userId}", method = RequestMethod.GET)
    public ApiResponse<List<UserMissionResponse>> getUserReceiveMission(@PathVariable String userId, @RequestParam Long roomId){
        return missionService.getUserReceiveMission(userId, roomId);
    }

    @ApiOperation(value = "사용자 대시보드", notes = "사용자/관리자의 대시보드 화면")
    @RequestMapping(value = "/dashBoard/{roll}", method = RequestMethod.GET)
    public ApiResponse getUserMissionCount(@ApiParam(allowableValues = "admin, user") @PathVariable String roll, @RequestParam String userId,
                                           @RequestParam Long roomId) {

        return missionService.getDashBoard(userId, roomId, UserStatus.getUserStatus(roll));
    }
}
