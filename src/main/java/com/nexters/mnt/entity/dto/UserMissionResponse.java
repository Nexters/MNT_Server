package com.nexters.mnt.entity.dto;

import com.nexters.mnt.entity.Manitto;
import com.nexters.mnt.entity.Mission;
import com.nexters.mnt.entity.User;
import com.nexters.mnt.entity.UserMission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMissionResponse {
    private UserMission userMission;
    private Integer userFruttoId;
    private User manitto;
    private Integer manittoFruttoId;
    private Long missionId;
}
