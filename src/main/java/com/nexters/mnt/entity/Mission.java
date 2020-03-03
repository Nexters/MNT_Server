package com.nexters.mnt.entity;

import com.fasterxml.jackson.annotation.*;
import com.nexters.mnt.entity.dto.MissionResponse;
import com.nexters.mnt.entity.dto.UserMissionResponse;
import com.nexters.mnt.service.RoomService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "mission_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Mission {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "room_tb_id")
    private Long roomId;

    @Column(name = "img_able")
    private Integer isAbleImg;

    @Column(name = "name")
    private String name;

    @JsonManagedReference
    @OneToMany(targetEntity = UserMission.class, cascade = CascadeType.ALL )
    @JoinColumn(name = "mission_tb_id")
    List<UserMission> userMissions;


    public MissionResponse convertToMissionResponse(List<UserMissionResponse> userMissionResponse){
        return new MissionResponse(this.id, this.roomId, this.isAbleImg, this.name, userMissionResponse);
    }
}
