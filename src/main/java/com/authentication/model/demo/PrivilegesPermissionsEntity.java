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
@Table(name = "privileges_permissions")
public class PrivilegesPermissionsEntity {
	
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "privilege_id")
    private Long privilegeId;

    @Column(name = "permission_id")
    private Long permissionId;

	public PrivilegesPermissionsEntity(Long id, Long privilegeId, Long permissionId) {
		this.id = id;
		this.privilegeId = privilegeId;
		this.permissionId = permissionId;
	}
}
