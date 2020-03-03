package com.nexters.mnt.entity.dto;

import com.nexters.mnt.entity.User;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManittoResponse {
    private int isCreater;
    private String manittoId;
    private User user;
    private Integer userFruttoId;
}
