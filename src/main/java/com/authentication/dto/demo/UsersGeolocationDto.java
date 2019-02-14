package com.authentication.dto.demo;

import java.sql.Timestamp;

import com.authentication.dto.BaseDto;
import com.authentication.model.demo.UsersGeolocationEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author TungBoom
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class UsersGeolocationDto extends BaseDto {
	
	private Long usersGeolocationId;
	private Long userId;
    private String latitude;
    private String longitude;
    private Timestamp createdTime;
	
	public UsersGeolocationEntity toEntity() {
		return new UsersGeolocationEntity(usersGeolocationId, userId, latitude, longitude, createdTime);
	}

	public UsersGeolocationDto(Long usersGeolocationId, Long userId, String latitude, String longitude,
			Timestamp createdTime) {
		this.usersGeolocationId = usersGeolocationId;
		this.userId = userId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createdTime = createdTime;
	}
}
