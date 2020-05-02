package com.nexters.mnt.controller;

import com.nexters.mnt.entity.*;
import com.nexters.mnt.entity.dto.ManittoResponse;
import com.nexters.mnt.entity.dto.UserResponse;
import com.nexters.mnt.repository.UserRepository;
import com.nexters.mnt.service.RoomService;
import com.nexters.mnt.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ApiResponse<List<ManittoResponse>> checkRoom(@RequestHeader String userId){
        return roomService.checkRoomExist(userId);
    }

    @ApiOperation(value = "방 만들기", notes = "방 코드를 응답값으로 보냄")
    @RequestMapping(value = "/room/make", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<String> makeRoom(@RequestBody Room room, @RequestParam("userId") String userId){
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
    public ApiResponse<List<ManittoResponse>> getUserList(@PathVariable Long roomId ){
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
    public ApiResponse<String> startRoom(@PathVariable Long roomId){
        return roomService.startRoom(roomId);
    }

    @ApiOperation(value = "방 종료하기")
    @RequestMapping(value = "/room/end/{roomId}", method = RequestMethod.GET)
    public void endRoom(@PathVariable Long roomId){
        roomService.endRoom(roomId);
    }

    @ApiOperation(value = "사용자의 마니또 정보 가져오기")
    @RequestMapping(value = "/user/manitto", method = RequestMethod.GET)
    public ApiResponse<UserResponse> getManitto(@RequestParam("roomId") Long roomId, @RequestParam("userId")  String userId){
        return roomService.getMyManitto(userId, roomId);
    }

    @ApiOperation(value = "사용자 방에서 삭제하기")
    @RequestMapping(value = "/room/user", method = RequestMethod.DELETE)
    public ApiResponse<String> deleteUserFromRoom(@RequestParam("roomId") Long roomId, @RequestParam("userId") String userId){
        try{
            roomService.removeUserFromRoom(roomId, userId);
        } catch (Exception e){
            return new ApiResponse<>(null, ApiStatus.Fail);
        }
        return new ApiResponse<>(null, ApiStatus.Ok);
    }


}
