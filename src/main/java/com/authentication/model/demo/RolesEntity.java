package com.authentication.model.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.authentication.dto.demo.RolesDto;

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
@Table(name = "roles")
public class RolesEntity {
	
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false, updatable = false)
    private Long roleId;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "status")
    private Long status;
    
    @Column(name = "description")
    private String description;
    
    public RolesDto toDto() {
        return new RolesDto(roleId, roleCode, roleName, status, description);
    }

    public RolesEntity(Long roleId, String roleCode, String roleName, Long status, String description) {
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.status = status;
        this.description = description;
    }
}
