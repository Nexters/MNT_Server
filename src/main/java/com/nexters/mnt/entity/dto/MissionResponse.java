package com.nexters.mnt.entity.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nexters.mnt.entity.UserMission;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
public class MissionResponse {
    private Long id;
    private Long roomId;
    private Integer isAbleImg;
    private String name;
    List<UserMissionResponse> userMissions;
}
