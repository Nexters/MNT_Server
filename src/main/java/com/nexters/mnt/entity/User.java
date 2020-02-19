package com.nexters.mnt.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_tb", schema = "mnt")
@Data
public class User {

    @Id
    @Column(name="id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(name = "fcm_token")
    private String fcmToken;

}
