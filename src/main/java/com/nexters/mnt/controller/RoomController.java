package com.nexters.mnt.controller;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.nexters.mnt.domain.*;
import com.nexters.mnt.repository.UserRepository;
import com.nexters.mnt.service.MissionService;
import com.nexters.mnt.service.RoomService;
import com.nexters.mnt.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;


    @ApiOperation(value = "카카오 회원가입")
    @RequestMapping(value = "/user/sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody User user){
        userService.signIn(user);
    }

    @ApiOperation(value = "로그인", notes = "임시")
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public void login(@RequestBody String token){

    }

    @ApiOperation(value = "사용자가 참여하는 방 조회", notes = "추후 확장을 고려해 리스트로 응답")
    @RequestMapping(value = "/room/check", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<Manitto>> checkRoom(@RequestHeader String userId){
        return roomService.checkRoomExist(userId);
    }

    @ApiOperation(value = "방 만들기", notes = "방 코드를 응답값으로 보냄")
    @RequestMapping(value = "/room/make", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Long> makeRoom(@RequestBody Room room, @RequestParam("userId") String userId){
        return roomService.makeRoom(room, userId);
    }

    @ApiOperation(value = "방 삭제", notes = "request body 없음")
    @RequestMapping(value = "/room/{roomId}", method = RequestMethod.DELETE)
    public void removeRoom(@PathVariable Long roomId){
        roomService.removeRoom(roomId);
    }

    @ApiOperation(value = "방에 참여하는 유저목록")
    @RequestMapping(value = "/room/user-list/{roomId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<Manitto>> getUserList(@PathVariable Long roomId ){
        return roomService.getUserList(roomId);
    }

    @ApiOperation(value = "방 참여하기")
    @RequestMapping(value = "/room/attend/{roomId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<Room> attendRoom(@PathVariable Long roomId, @RequestParam("userId") String userId){
        return roomService.attendRoom(roomId, userId);
    }

    @ApiOperation(value = "방 시작하기")
    @RequestMapping(value = "/room/start/{roomId}", method = RequestMethod.GET)
    public void startRoom(@PathVariable Long roomId){
        roomService.startRoom(roomId);
    }

    @ApiOperation(value = "사용자의 마니또 정보 가져오기")
    @RequestMapping(value = "/user/manitto", method = RequestMethod.GET)
    public ApiResponse<User> getManitto(@RequestParam("roomId") Long roomId, @RequestParam("userId")  String userId){
        return roomService.getMyManitto(userId, roomId);
    }

    @ApiOperation(value = "사용자 방에서 삭제하기")
    @RequestMapping(value = "/room/user", method = RequestMethod.DELETE)
    public void deleteUserFromRoom(@RequestParam("roomId") Long roomId, @RequestParam("userId") String userId){
        roomService.removeUserFromRoom(roomId, userId);
    }



}