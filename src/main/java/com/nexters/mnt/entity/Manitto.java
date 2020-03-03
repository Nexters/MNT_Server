package com.nexters.mnt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nexters.mnt.entity.dto.ManittoResponse;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

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

    @Column(name = "frutto_id")
    private Integer fruttoId;

    public Manitto(User user, Room room, int isCreater){
        this.isCreater = isCreater;
        this.user = user;
        this.room = room;
    }

    public ManittoResponse convertToManittoResponse(){
        return new ManittoResponse(this.isCreater, this.manittoId, this.user, this.fruttoId);
    }


}