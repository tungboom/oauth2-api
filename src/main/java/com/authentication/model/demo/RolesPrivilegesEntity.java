package com.authentication.model.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "roles_privileges")
public class RolesPrivilegesEntity {
	
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "privilege_id")
    private Long privilegeId;

	public RolesPrivilegesEntity(Long id, Long roleId, Long privilegeId) {
		this.id = id;
		this.roleId = roleId;
		this.privilegeId = privilegeId;
	}
}
