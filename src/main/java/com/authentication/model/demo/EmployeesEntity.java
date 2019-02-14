package com.authentication.model.demo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.authentication.dto.demo.EmployeesDto;

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
@Table(name = "employees")
public class EmployeesEntity {
	
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false, updatable = false)
    private Long employeeId;

    @Column(name = "employee_code", nullable = false, updatable = false)
    private String employeeCode;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;
    
    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "avatar_id")
    private Long avatarId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    public EmployeesDto toDto() {
		return new EmployeesDto(employeeId, employeeCode, firstName, lastName, email, phone,
				dateOfBirth, unitId, avatarId, userId);
	}

	public EmployeesEntity(Long employeeId, String employeeCode, String firstName, String lastName, String email,
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
