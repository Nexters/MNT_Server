package com.nexters.mnt.service;

import com.nexters.mnt.domain.Manitto;
import com.nexters.mnt.domain.Room;
import com.nexters.mnt.domain.User;
import com.nexters.mnt.repository.RoomRepository;
import com.nexters.mnt.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ManittoMapper {

    private RoomRepository roomRepository;
    private UserRepository userRepository;

    public ManittoMapper(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public Manitto mapFrom(Long roomId, String userId, int isCreater){
        Room room = roomRepository.findById(roomId).get();
        log.info(room);
        User user = userRepository.findById(userId).get();

        return new Manitto(user, room, isCreater);
    }
}
