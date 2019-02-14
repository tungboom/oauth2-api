package com.authentication.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.authentication.dto.UsersDto;

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
@Table(name = "users")
public class UsersEntity {

    static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "status", nullable = false)
    private Long status;
    
    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "created_time", insertable=true, updatable=false)
    private Timestamp createdTime;
    
    @Column(name = "created_user", insertable=true, updatable=false)
    private String createdUser;

    @Column(name = "updated_time", insertable=false, updatable=true)
    private Timestamp updatedTime;
    
    @Column(name = "updated_user", insertable=false, updatable=true)
    private String updatedUser;
    
    @Column(name = "sign_in_count")
    private Long signInCount;
    
    @Column(name = "current_sign_in_at")
    private Timestamp currentSignInAt;
    
    @Column(name = "current_sign_in_ip")
    private String currentSignInIp;
    
    @Column(name = "last_sign_in_at")
    private Timestamp lastSignInAt;
    
    @Column(name = "last_sign_in_ip")
    private String lastSignInIp;
    
    public UsersDto toDto() {
		return new UsersDto(userId, username, password, enabled, status, roleCode, createdTime,
				createdUser, updatedTime, updatedUser, signInCount, currentSignInAt, currentSignInIp, lastSignInAt,
				lastSignInIp);
	}

	public UsersEntity(Long userId, String username, String password, boolean enabled, Long status, String roleCode,
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
