package com.nexters.mnt.entity.dto;

import com.nexters.mnt.entity.User;
import lombok.*;
import lombok.Builder.Default;

import org.springframework.boot.context.properties.bind.DefaultValue;

import io.swagger.annotations.ApiModelProperty;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManittoResponse {
    @ApiModelProperty(value = "이 사용자가 이 방의 방장인지 유무 방장이라면 1")
    private int isCreater;
    @ApiModelProperty(value = "마니또(사용자가 돌봐야 하는 사람)에 대한 정보")
    private User manitto;
    @ApiModelProperty(value = "사용자에대한 정보")
    private User user;
    private Integer userFruttoId;
}
