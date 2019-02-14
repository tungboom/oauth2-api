package com.authentication.service.demo;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.ObjectUsersDto;
import com.authentication.repository.demo.EmployeesRepository;

/**
 * @author TungBoom
 *
 */
@Service
@Transactional
public class EmployeesServiceImpl implements EmployeesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeesServiceImpl.class);

    @Autowired
    protected EmployeesRepository employeesRepository;
    
    @Override
    public ObjectUsersDto getDetailUserByUsername(String username) {
    	LOGGER.debug("Request to getDetailUserByUsername : {}", username);
        return employeesRepository.getDetailUserByUsername(username);
    }
    
    @Override
    public Datatable search(Principal principal, ObjectUsersDto objectUsersDto) {
    	LOGGER.debug("Request to search : {}", objectUsersDto);
        return employeesRepository.search(principal, objectUsersDto);
    }
    
    @Override
    public ResultDto delete(Principal principal, Long userId) {
    	LOGGER.debug("Request to delete : {}", userId);
        return employeesRepository.delete(principal, userId);
    }

    @Override
    public ObjectUsersDto getDetail(Principal principal, Long userId) {
    	LOGGER.debug("Request to getDetail : {}", userId);
        return employeesRepository.getDetail(principal, userId);
    }
    
    @Override
    public ResultDto add(Principal principal, List<MultipartFile> files, String formDataJson) {
    	LOGGER.debug("Request to add : {}", formDataJson);
        return employeesRepository.add(principal, files, formDataJson);
    }

    @Override
    public ResultDto edit(Principal principal, List<MultipartFile> files, String formDataJson) {
    	LOGGER.debug("Request to edit : {}", formDataJson);
        return employeesRepository.edit(principal, files, formDataJson);
    }
    
    @Override
    public ResultDto checkExistUsername(String username, Long userId) {
    	LOGGER.debug("Request to check exist username : {}", username);
        return employeesRepository.checkExistUsername(username, userId);
    }
    
    @Override
    public ResultDto checkExistEmployeeCode(String employeeCode, Long userId) {
    	LOGGER.debug("Request to check exist employeeCode : {}", employeeCode);
        return employeesRepository.checkExistEmployeeCode(employeeCode, userId);
    }
}
