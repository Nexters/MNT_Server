package com.nexters.mnt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "mission_TB")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mission {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "room_TB_id")
    private Long roomId;

    @Column(name = "img_able")
    private Integer isAbleImg;

    @Column(name = "name")
    private String name;

    @OneToMany(targetEntity = UserMission.class, cascade = CascadeType.ALL )
    @JoinColumn(name = "mission_TB_id")
    @Where(clause = "user_done = 1")
    List<UserMission> userMissions;

}
