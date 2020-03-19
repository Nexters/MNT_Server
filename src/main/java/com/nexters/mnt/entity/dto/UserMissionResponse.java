package com.nexters.mnt.entity.dto;

import com.nexters.mnt.entity.Manitto;
import com.nexters.mnt.entity.Mission;
import com.nexters.mnt.entity.User;
import com.nexters.mnt.entity.UserMission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMissionResponse {

    @ApiModelProperty(value = "사용자가 수행한 미션 내용")
    private UserMission userMission;
    @ApiModelProperty(value = "사용자의 fruttoId")
    private Integer userFruttoId;
    @ApiModelProperty(value = "마니또에 대한 정보")
    private UserResponse manitto;
    @ApiModelProperty(value = "미션 ID")
    private Long missionId;
    @ApiModelProperty(value = "미션 이름")
    private String missionName;
}
