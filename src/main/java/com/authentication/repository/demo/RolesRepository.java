package com.authentication.repository.demo;

import java.security.Principal;

import org.springframework.stereotype.Repository;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.RolesDto;

/**
 * @author TungBoom
 *
 */
@Repository
public interface RolesRepository {
	
	Datatable search(Principal principal, RolesDto rolesDto);
    ResultDto delete(Principal principal, Long roleId);
    RolesDto getDetail(Principal principal, Long roleId);
    ResultDto add(Principal principal, RolesDto rolesDto);
    ResultDto edit(Principal principal, RolesDto rolesDto);
}
