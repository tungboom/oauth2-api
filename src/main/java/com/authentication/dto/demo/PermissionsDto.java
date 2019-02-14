package com.authentication.dto.demo;

import com.authentication.dto.BaseDto;
import com.authentication.model.demo.PermissionsEntity;

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
public class PermissionsDto extends BaseDto {
	
	private Long permissionId;
    private String permissionCode;
    private String permissionName;
    private Long status;
    private String description;
    
    public PermissionsEntity toEntity() {
		return new PermissionsEntity(permissionId, permissionCode, permissionName, status, description);
	}

	public PermissionsDto(Long permissionId, String permissionCode, String permissionName, Long status,
			String description) {
		this.permissionId = permissionId;
		this.permissionCode = permissionCode;
		this.permissionName = permissionName;
		this.status = status;
		this.description = description;
	}
}
