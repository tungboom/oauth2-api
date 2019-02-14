package com.authentication.service.demo;

import java.security.Principal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.ObjectUsersDto;

/**
 * @author TungBoom
 *
 */
public interface EmployeesService {
	ObjectUsersDto getDetailUserByUsername(String username);
	
	Datatable search(Principal principal, ObjectUsersDto objectUsersDto);
    ResultDto delete(Principal principal, Long userId);
    ObjectUsersDto getDetail(Principal principal, Long userId);
    ResultDto add(Principal principal, List<MultipartFile> files, String formDataJson);
    ResultDto edit(Principal principal, List<MultipartFile> files, String formDataJson);
    
    ResultDto checkExistUsername(String username, Long userId);
    ResultDto checkExistEmployeeCode(String employeeCode, Long userId);
}
