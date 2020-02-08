package com.nexters.mnt.domain;


import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_TB")
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
