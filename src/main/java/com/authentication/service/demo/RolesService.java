package com.authentication.service.demo;

import java.security.Principal;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.RolesDto;

/**
 * @author TungBoom
 *
 */
public interface RolesService {

	Datatable search(Principal principal, RolesDto rolesDto);
    ResultDto delete(Principal principal, Long roleId);
    RolesDto getDetail(Principal principal, Long roleId);
    ResultDto add(Principal principal, RolesDto rolesDto);
    ResultDto edit(Principal principal, RolesDto rolesDto);
}
