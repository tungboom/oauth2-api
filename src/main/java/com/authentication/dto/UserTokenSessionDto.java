package com.authentication.dto;

import com.authentication.dto.demo.ObjectUsersDto;
import com.authentication.model.UserTokenSessionEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserTokenSessionDto extends UserTokenSessionEntity {
	private ObjectUsersDto objectUsersDto;
}
