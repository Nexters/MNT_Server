package com.nexters.mnt.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

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

}
