package com.nexters.mnt.domain;

import com.nexters.mnt.domain.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;

import java.sql.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "room_tb")
@Data
public class Room {

    @Id
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

    @Column(name = "is_start")
    private Integer isStart;


}
