package com.authentication.dto.demo;

import java.sql.Timestamp;

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
public class EmployeesDto {

    private Long employeeId;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Timestamp dateOfBirth;
    private Long unitId;
    private Long avatarId;
    private Long userId;
    
    public EmployeesEntity toEntity() {
		return new EmployeesEntity(employeeId, employeeCode, firstName, lastName, email, phone, dateOfBirth, unitId, avatarId, userId);
	}

	public EmployeesDto(Long employeeId, String employeeCode, String firstName, String lastName, String email,
			String phone, Timestamp dateOfBirth, Long unitId, Long avatarId, Long userId) {
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.unitId = unitId;
		this.avatarId = avatarId;
		this.userId = userId;
	}
}
