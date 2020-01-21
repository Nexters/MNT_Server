package com.nexters.mnt.domain;


import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_TB")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_pic")
    private String profilePic;

}
