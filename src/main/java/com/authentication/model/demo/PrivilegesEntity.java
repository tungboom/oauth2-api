package com.authentication.model.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.authentication.dto.demo.PrivilegesDto;

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
@Table(name = "privileges")
public class PrivilegesEntity {
	
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "privilege_id", nullable = false, updatable = false)
    private Long privilegeId;

    @Column(name = "privilege_code")
    private String privilegeCode;

    @Column(name = "privilege_name")
    private String privilegeName;

    @Column(name = "status")
    private Long status;
    
    @Column(name = "description")
    private String description;
    
    public PrivilegesDto toDto() {
        return new PrivilegesDto(privilegeId, privilegeCode, privilegeName, status, description);
    }

    public PrivilegesEntity(Long privilegeId, String privilegeCode, String privilegeName, Long status, String description) {
        this.privilegeId = privilegeId;
        this.privilegeCode = privilegeCode;
        this.privilegeName = privilegeName;
        this.status = status;
        this.description = description;
    }
}
