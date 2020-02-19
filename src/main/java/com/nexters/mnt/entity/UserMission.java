package com.nexters.mnt.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_mission_tb")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class UserMission {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="room_tb_id")
    private Long roomId;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="user_tb_id")
    private User userId;

    @JsonBackReference
    @ManyToOne(targetEntity = Mission.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_tb_id", referencedColumnName = "id")
    private Mission missionId;

    @Column(name = "user_done")
    private Integer userDone;

    @LastModifiedDate
    @Column(name = "user_done_time")
    private Date userDoneTime;

    @Column(name = "mission_img")
    private String missionImg;

    @Column(name = "content")
    private String content;

    public UserMission(Long roomId, User userId, Mission missionId ){
        this.roomId = roomId;
        this.userId = userId;
        this.missionId = missionId;
    }

}
