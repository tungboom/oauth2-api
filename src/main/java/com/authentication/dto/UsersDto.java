package com.authentication.dto;

import java.sql.Timestamp;

import com.authentication.model.UsersEntity;

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
public class UsersDto extends BaseDto {
	
	private Long userId;
    private String username;
    private String password;
	private boolean enabled;
	private Long status;
    private String roleCode;
    private Timestamp createdTime;
    private String createdUser;
    private Timestamp updatedTime;
    private String updatedUser;
    private Long signInCount;
    private Timestamp currentSignInAt;
    private String currentSignInIp;
    private Timestamp lastSignInAt;
    private String lastSignInIp;
    
	public UsersEntity toEntity() {
		return new UsersEntity(userId, username, password, enabled, status, roleCode, createdTime, createdUser, updatedTime, updatedUser, signInCount, 
				currentSignInAt, currentSignInIp, lastSignInAt, lastSignInIp);
	}

	public UsersDto(Long userId, String username, String password, boolean enabled, Long status, String roleCode,
			Timestamp createdTime, String createdUser, Timestamp updatedTime, String updatedUser, Long signInCount,
			Timestamp currentSignInAt, String currentSignInIp, Timestamp lastSignInAt,
			String lastSignInIp) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.status = status;
		this.roleCode = roleCode;
		this.createdTime = createdTime;
		this.createdUser = createdUser;
		this.updatedTime = updatedTime;
		this.updatedUser = updatedUser;
		this.signInCount = signInCount;
		this.currentSignInAt = currentSignInAt;
		this.currentSignInIp = currentSignInIp;
		this.lastSignInAt = lastSignInAt;
		this.lastSignInIp = lastSignInIp;
	}
}
