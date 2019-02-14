package com.authentication.dto.demo;

import java.sql.Timestamp;

import com.authentication.dto.BaseDto;
import com.authentication.model.UsersEntity;
import com.authentication.model.demo.EmployeesEntity;

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
public class ObjectUsersDto extends BaseDto {
	
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

    private Long employeeId;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Timestamp dateOfBirth;
    private Long unitId;
    private Long avatarId;

    private String roleName;
    private String fullName;
    private String unitCode;
    private String unitName;
    private String rePassword;
    private String avatarName;
    private String avatarType;
    private String avatarPath;
    private String avatarBase64;
    private String enabledString;

    public EmployeesEntity toEmployeesEntity() {
        return new EmployeesEntity(employeeId, employeeCode, firstName, lastName, email, phone, dateOfBirth, unitId, avatarId, userId);
    }

    public UsersEntity toUsersEntity() {
        return new UsersEntity(userId, username, password, enabled, status, roleCode, createdTime, createdUser, updatedTime, updatedUser, signInCount, 
				currentSignInAt, currentSignInIp, lastSignInAt, lastSignInIp);
    }
}
