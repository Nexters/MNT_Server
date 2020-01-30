package com.nexters.mnt.domain;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

import java.sql.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_mission_TB")
@Data
public class UserMission {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="room_TB_id")
    private Long roomId;

    @Column(name="user_TB_id")
    private String userId;

    @Column(name = "mission_TB_id")
    private String missionId;

    @Column(name = "user_done")
    private Integer userDone;

    @LastModifiedDate
    @Column(name = "user_done_time")
    private Date userDoneTime;

    @Column(name = "mission_img")
    private String missionImg;

}
