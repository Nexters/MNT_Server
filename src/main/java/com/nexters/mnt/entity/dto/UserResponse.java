package com.nexters.mnt.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class UserResponse {
	private final String id;
	private final String name;
	private final Integer fruttoId;
	private String fcmToken;
}
