package com.nexters.mnt.domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

import java.sql.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "room_TB")
@Data
public class Room {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name ="name")
    private String name;

    @Column(name="max_people_num")
    private Long maxPeople;

    @Column(name="end_day")
    private Date endDay;

    @Column(name="start_day")
    private Date startDay;

    @Column(name = "is_done")
    private Integer isDone;


}
