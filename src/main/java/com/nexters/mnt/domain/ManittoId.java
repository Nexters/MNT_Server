package com.nexters.mnt.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class ManittoId implements Serializable {

    private Long roomId;
    private String userId;

    public ManittoId(){

    }
    public ManittoId(Long roomId, String userId){
        this.roomId = roomId;
        this.userId = userId;
    }

}
