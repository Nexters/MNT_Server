package com.nexters.mnt.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nexters.mnt.controller.ApiStatus;
import com.nexters.mnt.domain.*;
import com.nexters.mnt.repository.ManittoRepository;
import com.nexters.mnt.repository.RoomRepository;
import com.nexters.mnt.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j2
public class RoomService {

    private final RoomRepository roomRepository;
    private final ManittoRepository manittoRepository;
    private final ManittoMapper manittoMapper;
    @Autowired
    private UserRepository userRepository;

    public RoomService(RoomRepository roomRepository, ManittoRepository manittoRepository, ManittoMapper manittoMapper) {
        this.roomRepository = roomRepository;
        this.manittoRepository = manittoRepository;
        this.manittoMapper = manittoMapper;
    }



    @Transactional
    public ApiResponse<Long> makeRoom(Room room, String userId){
        Long code = (long)0;
        do {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                stringBuilder.append((int) (Math.random() *(9)));
                log.info(stringBuilder);
            }
            code = Long.parseLong(stringBuilder.toString());
            log.info(code);
        }while (checkRoomCode(code) != null);
        log.info(room.getName());
        room.setId(code);
        roomRepository.save(room);
        manittoRepository.save(manittoMapper.mapFrom(code, userId, 1));
        return new ApiResponse(code, ApiStatus.Ok);
    }

    public Room checkRoomCode(Long code){
        return roomRepository.findById(code).orElse(null);
    }

    public ApiResponse<List<Manitto>> checkRoomExist(String userId){
        List<Manitto> manittos = manittoRepository.findByUser(userId).orElse(null);
        if(manittos == null)
            return new ApiResponse<>(manittos, ApiStatus.DataNotFound);
        else
            return new ApiResponse<>(manittos, ApiStatus.Ok);
    }

    @Transactional
    public void removeRoom(Long roomId){
        manittoRepository.deleteByRoom(roomId);
        roomRepository.deleteById(roomId);
    }

    public void removeUserFromRoom(Long roomId, String userId){
        manittoRepository.deleteByRoomIdAndUser(roomId, userId);
    }

    public ApiResponse<List<Manitto>> getUserList(Long roomId){
        List<Manitto> manittos = manittoRepository.findByRoom(roomId).orElse(null);
        if(manittos == null)
            return new ApiResponse<>(manittos, ApiStatus.DataNotFound);
        else
            return new ApiResponse<>(manittos, ApiStatus.Ok);
    }

    @Transactional
    public ApiResponse<Room> attendRoom(Long roomId, String userId){
        Room room = null;
        if((room = roomRepository.findByIdAndIsStartAndIsDone(roomId, 0,0).orElse(null)) == null)
            return new ApiResponse<>(null, ApiStatus.NotEstablishedRoom);

        if(manittoRepository.findByRoomAndUser(userId, roomId).orElse(null) != null)
            return new ApiResponse<>(null, ApiStatus.DuplicateRoom);

        if(room.getMaxPeople() == manittoRepository.countByRoom(roomId))
            return new ApiResponse<>(null, ApiStatus.FULL);

        manittoRepository.save(manittoMapper.mapFrom(roomId, userId, 0));
        return new ApiResponse<>(roomRepository.findById(roomId).get(), ApiStatus.Ok);
    }

    @Transactional
    public ApiResponse<String> startRoom(Long roomId){
        List<Manitto> manittos = manittoRepository.findByRoomAndIsCreaterIs(roomId, 0);

        if(manittos.size() <= 1)
            return new ApiResponse<>(null, ApiStatus.NotEnoughToStart);
        for(int i = 0; i < manittos.size(); i++){
            if(i == manittos.size()-1){
                manittoRepository.updateManittoId(manittos.get(i).getUser().getId(), manittos.get(0).getUser().getId(), roomId);
            } else{
                manittoRepository.updateManittoId(manittos.get(i).getUser().getId(), manittos.get(i+1).getUser().getId(), roomId);
            }
        }

        roomRepository.updateStartRoom(roomId);
        return new ApiResponse<>(null, ApiStatus.Ok);
    }

    public void endRoom(Long roomId){
        roomRepository.updateEndRoom(roomId);
    }

    @Transactional
    public ApiResponse<User> getMyManitto(String userId, Long roomId){
        String user = manittoRepository.findByRoomAndUser(userId, roomId).get().getManittoId();
        return new ApiResponse<>(userRepository.findById(user).get(), ApiStatus.Ok);
    }


    @Scheduled(cron = "0 0 12 * * *")
    public void roomScheduler(){
        roomRepository.updateStartRoomAuto();
        roomRepository.updateEndRoomAuto();
    }


}
