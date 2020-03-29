package com.nexters.mnt.controller;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum UserStatus {
	Admin("admin"),
	User("user");

	private String label;

	public static UserStatus getUserStatus(String label){

		if(Admin.label.equals(label)){
			return Admin;
		} else {
			return User;
		}
	}
}
