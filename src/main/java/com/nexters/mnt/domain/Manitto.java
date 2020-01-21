package com.nexters.mnt.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "manitto_TB")
@Getter
public class Manitto {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "room_TB_id")
    private Long roomId;

    @Column(name="user_TB_id")
    private String userId;

    @Column(name = "manitto_id")
    private String manittoId;
}
