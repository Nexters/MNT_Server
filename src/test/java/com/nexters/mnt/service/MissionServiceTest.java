package com.nexters.mnt.service;

import com.nexters.mnt.domain.Mission;
import com.nexters.mnt.domain.UserMission;
import com.nexters.mnt.repository.MissionRepository;
import com.nexters.mnt.repository.UserMissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MissionServiceTest {

    @Autowired
    MissionService missionService;

    @Autowired
    MissionRepository missionRepository;

    @Autowired
    UserMissionRepository userMissionRepository;

    @Test
    void sendMission() {
        UserMission userMission = new UserMission();
        userMission.setUserId("1");
        userMission.setRoomId((long)83722);
        userMission.setContent("안녕");
        userMission.setMissionId(missionRepository.findById((long)4).get());
        missionService.sendMission(userMission);

        assertEquals(userMissionRepository.findByRoomIdAndUserIdAndMissionId((long)83722, "1", (long)4).getUserDone(), 1);

    }
}