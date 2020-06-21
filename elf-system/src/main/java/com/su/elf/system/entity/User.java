package com.su.elf.system.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class User {

	private int id;
	private String username;
	private String password;
	private String nickname = "";
	private String phone = "";
	private String avatarUrl = "";
	private int gender;
	private int isSuper;
	private int readonly;
	private String createTime;
	private List<Integer> roles;

}