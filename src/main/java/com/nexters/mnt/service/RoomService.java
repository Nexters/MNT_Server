package com.nexters.mnt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nexters.mnt.controller.ApiStatus;
import com.nexters.mnt.entity.ApiResponse;
import com.nexters.mnt.entity.Manitto;
import com.nexters.mnt.entity.Room;
import com.nexters.mnt.entity.User;
import com.nexters.mnt.entity.dto.ManittoResponse;
import com.nexters.mnt.entity.dto.UserResponse;
import com.nexters.mnt.repository.ManittoRepository;
import com.nexters.mnt.repository.MissionRepository;
import com.nexters.mnt.repository.RoomRepository;
import com.nexters.mnt.repository.UserMissionRepository;
import com.nexters.mnt.repository.UserRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class RoomService {

	private final RoomRepository roomRepository;
	private final ManittoRepository manittoRepository;
	private final ManittoMapper manittoMapper;
	private final UserRepository userRepository;
	private final UserMissionRepository userMissionRepository;
	private final MissionRepository missionRepository;

	@Transactional
	public ApiResponse<String> makeRoom(Room room, String userId) {
		String code = "";
		do {
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < 5; i++) {
				stringBuilder.append((int) (Math.random() * (9)));
				log.info(stringBuilder);
			}
			code = stringBuilder.toString();
		} while (checkRoomCode(Long.parseLong(code)) != null);
		log.info(room.getName());
		room.setId(Long.parseLong(code));
		roomRepository.save(room);
		manittoRepository.save(manittoMapper.mapFrom(Long.parseLong(code), userId, 1));
		return new ApiResponse(code, ApiStatus.Ok);
	}

	public Room checkRoomCode(Long code) {
		return roomRepository.findById(code).orElse(null);
	}

	public ApiResponse<List<ManittoResponse>> checkRoomExist(String userId) {
		List<Manitto> manittos = manittoRepository.findByUser(userId).orElse(null);
		List<ManittoResponse> manittoResponses = new ArrayList<>();
		if (manittos == null) {
			return new ApiResponse<>(null, ApiStatus.DataNotFound);
		}
		for (Manitto manitto : manittos) {
			Manitto manittoInfo = null;
			if (manitto.getManittoId() != null) {
				manittoInfo = getManitto(manitto.getManittoId(), manitto.getRoom().getId());
			}
			manittoResponses.add(manitto.convertToManittoResponse(
					new UserResponse(manitto.getManittoId(), manittoInfo != null ? manittoInfo.getUser().getName() : null,
					                 manittoInfo != null ? manittoInfo.getFruttoId() : null)));
		}
		return new ApiResponse<>( manittoResponses, ApiStatus.Ok);
	}

	@Transactional
	public void removeRoom(Long roomId) {
		manittoRepository.deleteByRoom(roomId);
		userMissionRepository.deleteUserMissionsByRoomId(roomId);
		missionRepository.deleteMissionsByRoomId(roomId);
		roomRepository.deleteById(roomId);
	}

	@Transactional
	public void removeUserFromRoom(Long roomId, String userId) {
		Room room = roomRepository.findById(roomId).get();
		if(room.getIsStart() == 0) {
			manittoRepository.deleteByRoomIdAndUser(roomId, userId);
			return;
		}
		manittoRepository.updateIsCreater(userId, roomId);
	}

	public ApiResponse<List<ManittoResponse>> getUserList(Long roomId) {
		List<Manitto> manittos = manittoRepository.findByRoom(roomId).orElse(null);
		if (manittos == null) {
			return new ApiResponse<>(null, ApiStatus.DataNotFound);
		}

		List<ManittoResponse> manittoResponses = new ArrayList<>();
		for (Manitto manitto : manittos) {
			Manitto manittoInfo = null;
			if (manitto.getManittoId() != null) {
				manittoInfo = getManitto(manitto.getManittoId(), manitto.getRoom().getId());
			}
			manittoResponses.add(manitto.convertToManittoResponse(
					new UserResponse(manitto.getManittoId(), manittoInfo != null ? manittoInfo.getUser().getName() : null,
					                 manittoInfo != null ? manittoInfo.getFruttoId() : null)));
		}
		return new ApiResponse<>( manittoResponses, ApiStatus.Ok);
	}

	@Transactional
	public ApiResponse<Room> attendRoom(Long roomId, String userId) {
		Room room = null;
		if ((room = roomRepository.findByIdAndIsStartAndIsDone(roomId, 0, 0).orElse(null)) == null) {
			return new ApiResponse<>(null, ApiStatus.NotEstablishedRoom);
		}

		if (manittoRepository.findByRoomAndUser(userId, roomId).orElse(null) != null) { return new ApiResponse<>(null, ApiStatus.DuplicateRoom); }

		if (room.getMaxPeople() == manittoRepository.countByRoom(roomId)) { return new ApiResponse<>(null, ApiStatus.FULL); }

		manittoRepository.save(manittoMapper.mapFrom(roomId, userId, 0));
		return new ApiResponse<>(roomRepository.findById(roomId).get(), ApiStatus.Ok);
	}

	@Transactional
	public ApiResponse<String> startRoom(Long roomId) {
		List<Manitto> manittos = manittoRepository.findByRoomAndIsCreaterIs(roomId, 0);

		if (manittos.size() <= 1) { return new ApiResponse<>(null, ApiStatus.NotEnoughToStart); }
		List<Integer> idList = new ArrayList<>();
		for(int i = 2; i < 15; i++){
			idList.add(i);
		}
		Random random = new Random();
		for (int i = 0; i < manittos.size(); i++) {
			int idx = random.nextInt(idList.size() > 1 ? idList.size()-1:1);
			if (i == manittos.size() - 1) {
				manittoRepository.updateManittoId(manittos.get(i).getUser().getId(), manittos.get(0).getUser().getId(), roomId, idList.get(idx));
			} else {
				manittoRepository.updateManittoId(manittos.get(i).getUser().getId(), manittos.get(i + 1).getUser().getId(), roomId, idList.get(idx));
			}
			idList.remove(idx);
		}

		roomRepository.updateStartRoom(roomId);
		return new ApiResponse<>(null, ApiStatus.Ok);
	}

	public void endRoom(Long roomId) {
		roomRepository.updateEndRoom(roomId);
	}

	public ApiResponse<UserResponse> getMyManitto(String userId, Long roomId) {
		Manitto manitto = manittoRepository.findByRoomAndUser(userId, roomId).orElse(null);
		if(manitto == null){
			return new ApiResponse<>(null, ApiStatus.Fail);
		}
		User user = userRepository.findById(manitto.getManittoId()).get();
		Integer fruttoId = manittoRepository.findByRoomAndUser(user.getId(), roomId).get().getFruttoId();
		UserResponse userResponse = new UserResponse(user.getId(), user.getName(), fruttoId);
		userResponse.setFcmToken(user.getFcmToken());
		return new ApiResponse<>(userResponse ,ApiStatus.Ok);
	}

	public Manitto getManitto(String userId, Long roomId) {
		return manittoRepository.findByRoomAndUser(userId, roomId).get();
	}

	@Scheduled(cron = "0 0 12 * * *")
	public void roomScheduler() {
		List<Room> roomList = roomRepository.findRoomByStartDay();
		List<String> fcmTokens = new ArrayList<>();
		roomList.forEach(r ->startRoom(r.getId()));
		roomRepository.updateStartRoomAuto();
		roomRepository.updateEndRoomAuto();
	}

}
