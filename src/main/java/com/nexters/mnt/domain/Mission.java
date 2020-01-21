package com.nexters.mnt.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "mission_TB")
@Getter
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


}
