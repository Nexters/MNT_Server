package com.nexters.mnt.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;

import java.sql.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_mission_TB")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserMission {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="room_TB_id")
    private Long roomId;

    @Column(name="user_TB_id")
    private String userId;

    @ManyToOne(targetEntity = Mission.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_TB_id", referencedColumnName = "id")
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

    public UserMission(Long roomId, String userId, Mission missionId ){
        this.roomId = roomId;
        this.userId = userId;
        this.missionId = missionId;
    }

}
