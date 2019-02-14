package com.authentication.model.demo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.authentication.dto.demo.UsersGeolocationDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author TungBoom
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users_geolocation")
public class UsersGeolocationEntity {
	
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "users_geolocation_id", nullable = false, updatable = false)
    private Long usersGeolocationId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "latitude", nullable = false, updatable = false)
    private String latitude;

    @Column(name = "longitude", nullable = false, updatable = false)
    private String longitude;
    
    @Column(name = "created_time", insertable=true, updatable=false)
    private Timestamp createdTime;
    
    public UsersGeolocationDto toDto() {
		return new UsersGeolocationDto(usersGeolocationId, userId, latitude, longitude, createdTime);
	}

	public UsersGeolocationEntity(Long usersGeolocationId, Long userId, String latitude, String longitude,
			Timestamp createdTime) {
		this.usersGeolocationId = usersGeolocationId;
		this.userId = userId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createdTime = createdTime;
	}
}
