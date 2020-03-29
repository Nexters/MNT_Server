package com.nexters.mnt.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.boot.context.properties.bind.DefaultValue;

import com.nexters.mnt.entity.Room;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashBoardResponse {
	private Long missionCountOfAll = 0L;
	private Long missionCountOfUserSend = 0L;
	private Long missionCountOfUserReceive = 0L;
	private Room room;
}
