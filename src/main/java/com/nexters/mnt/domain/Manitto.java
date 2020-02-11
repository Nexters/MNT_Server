package com.nexters.mnt.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nexters.mnt.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "manitto_tb")
@Data
@NoArgsConstructor
public class Manitto {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_tb_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Room room;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tb_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User user;

    @Column(name = "manitto_id")
    private String manittoId;

    @Column(name = "is_creater")
    private int isCreater;


    public Manitto(User user, Room room, int isCreater){
        this.isCreater = isCreater;
        this.user = user;
        this.room = room;
    }


}
