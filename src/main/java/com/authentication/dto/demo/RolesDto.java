package com.authentication.dto.demo;

import java.util.List;

import com.authentication.dto.BaseDto;
import com.authentication.model.demo.RolesEntity;

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
public class RolesDto extends BaseDto {
	
	private Long roleId;
    private String roleCode;
    private String roleName;
    private Long status;
    private String description;
    List<PrivilegesDto> listPrivilegesDto;
    
    public RolesEntity toEntity() {
		return new RolesEntity(roleId, roleCode, roleName, status, description);
	}

	public RolesDto(Long roleId, String roleCode, String roleName, Long status, String description) {
		this.roleId = roleId;
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.status = status;
		this.description = description;
	}
}
