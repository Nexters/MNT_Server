package com.nexters.mnt.service;

import com.google.protobuf.Api;
import com.nexters.mnt.common.FirebaseUtil;
import com.nexters.mnt.controller.ApiStatus;
import com.nexters.mnt.entity.*;
import com.nexters.mnt.entity.dto.*;
import com.nexters.mnt.repository.ManittoRepository;
import com.nexters.mnt.repository.MissionRepository;
import com.nexters.mnt.repository.UserMissionRepository;
import com.nexters.mnt.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.transaction.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
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
	private UserRepository userRepository;

	@Autowired
	FirebaseUtil firebaseUtil;

	@Autowired
	AwsS3Service awsS3Service;

	@Value("${aws.s3.url}")
	String s3Url;

	@Transactional
	public ApiResponse<List<UserMissionResponse>> getTimeLine(Long roomId) {

		List<UserMission> userMissions = userMissionRepository.findByRoomIdAndUserDoneIsOrderByUserDoneTime(roomId, 1);
		List<UserMissionResponse> userMissionResponses = new ArrayList<>();

		for (UserMission userMission : userMissions) {
			Manitto manitto = roomService.getManitto(userMission.getUserId().getId(), roomId);
			User manittoInfo = userRepository.getOne(manitto.getManittoId());
			Integer manittoFruttoId = roomService.getManitto(manitto.getManittoId(), roomId).getFruttoId();
			userMissionResponses.add(userMission.convertToUserMissionResponse(
					manitto.convertToManittoResponse(new UserResponse(manittoInfo.getId(), manittoInfo.getName(), manittoFruttoId))));
		}
		return new ApiResponse<>(userMissionResponses, ApiStatus.Ok);
	}

	@Transactional
	public ApiResponse<String> makeMission(MissionRequest missionRequest) {
		List<ManittoResponse> users = roomService.getUserList(missionRequest.getRoomId()).getData();
		if (users == null) {
			return new ApiResponse<>(null, ApiStatus.DataNotFound);
		}
		List<UserMission> userMissionList = new ArrayList<>();
		Mission mission = missionRequest.convertToMission();
		for (ManittoResponse manitto : users) {
			userMissionList.add(new UserMission(mission.getRoomId(), manitto.getUser(), mission));
		}
		mission.setUserMissions(userMissionList);
		missionRepository.save(mission);
        /*firebaseUtil.sendFcmToAll(userMissionList.stream()
        .map(UserMission::getUserId).map(User::getFcmToken).collect(Collectors.toList()),"마니또!", "테스트~!!" );*/
		return new ApiResponse<>("", ApiStatus.Ok);
	}

	@Transactional
	public ApiResponse<List<MissionResponse>> getMissionGroupBy(Long roomId) {
		List<Mission> missionList = missionRepository.findByRoomId(roomId);
		List<MissionResponse> missionResponses = new ArrayList<>();
		for (Mission mission : missionList) {
			List<UserMissionResponse> userMissionResponses = new ArrayList<>();
			for (UserMission userMission : mission.getUserMissions()) {
				Manitto manitto = roomService.getManitto(userMission.getUserId().getId(), roomId);
                if (manitto.getIsCreater() == 1) { continue; }
				log.info(manitto.toString());
				Integer manittoFruttoId = roomService.getManitto(manitto.getManittoId(), roomId).getFruttoId();
				User manittoInfo = userRepository.getOne(manitto.getManittoId());
                userMissionResponses.add(userMission.convertToUserMissionResponse(
                        manitto.convertToManittoResponse(new UserResponse(manittoInfo.getId(), manittoInfo.getName(), manittoFruttoId))));
			}
			missionResponses.add(mission.convertToMissionResponse(userMissionResponses));
		}
		return new ApiResponse<>(missionResponses, ApiStatus.Ok);
	}

	@Transactional
	public ApiResponse<String> sendMission(UserMissionRequest mission) {
		String fileName = null;
		mission.setDate(new Date());
		if (mission.getImg() != null) {
			log.info(mission.toString());
			try {
				Date date = new Date();
				fileName = mission.getImg().getOriginalFilename() + date.toString();
				awsS3Service.uploadObject(mission.getImg(), mission.getImg().getOriginalFilename() + date.toString());
				fileName = s3Url + fileName;
			} catch (IOException e) {
				log.info(e.getMessage());
				return new ApiResponse<>(null, ApiStatus.Fail);
			}
		}
		try {
			userMissionRepository.updateUserMission(mission, fileName);
			User user = roomService.getMyManitto(mission.getUserId(), mission.getRoomId()).getData();
		} catch (Exception e) {
			log.info(e.getMessage());
			return new ApiResponse<>(null, ApiStatus.Fail);
		}
//        firebaseUtil.sendFcmUserToUser(user.getFcmToken(), "마니또!", "테스트~!!");
		return new ApiResponse<>(null, ApiStatus.Ok);
	}

	@Transactional
	public ApiResponse<List<UserMissionResponse>> getUserMission(String userId, Long roomId) {
		return new ApiResponse<>(userMissionRepository.findByUserIdAndRoomId(userId, roomId).stream().map(UserMission::convertToUserMissionResponse)
		                                              .collect(Collectors.toList()), ApiStatus.Ok);
	}

	@Transactional
	public ApiResponse<List<UserMissionResponse>> getUserReceiveMission(String userId, Long roomId) {
		String manittoId = manittoRepository.findByManittoIdAndRoom(userId, roomId).get().getUser().getId();
		return new ApiResponse<>(userMissionRepository.findByUserIdAndRoomId(manittoId, roomId).stream().map(UserMission::convertToUserMissionResponse)
		                                              .collect(Collectors.toList()), ApiStatus.Ok);
	}
}
