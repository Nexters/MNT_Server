package com.nexters.mnt.entity.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nexters.mnt.entity.UserMission;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

import io.swagger.annotations.ApiModelProperty;

@Data
@AllArgsConstructor
public class MissionResponse {
    @ApiModelProperty(value = "mission Id")
    private long id;
    @ApiModelProperty(value = "room Id")
    private long roomId;
    @ApiModelProperty(value = "미션에 이미지가 들어가야하는지")
    private Integer isAbleImg;
    @ApiModelProperty(value = "미션 이름")
    private String name;
    @ApiModelProperty(value = "이 미션에 대해 사용자들이 수행하였는지에 대한 값과 수행 내용")
    List<UserMissionResponse> userMissions;
}
