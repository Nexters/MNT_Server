package com.nexters.mnt.entity.dto;

import com.nexters.mnt.entity.Mission;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MissionRequest {
    private int isAbleImg;
    private String name;
    private Long roomId;

    public Mission convertToMission(){
        Mission mission = new Mission();
        mission.setIsAbleImg(this.isAbleImg);
        mission.setName(this.name);
        mission.setRoomId(this.roomId);
        return mission;
    }

}
