package com.authentication.dto.demo;

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
public class ObjectRolesDto {
	
	private Long roleId;
    private String roleCode;
    private String roleName;
    
    private Long privilegeId;
    private String privilegeCode;
    private String privilegeName;
    
    private Long permissionId;
    private String permissionCode;
    private String permissionName;
}
