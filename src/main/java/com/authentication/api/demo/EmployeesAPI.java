package com.authentication.api.demo;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.ObjectUsersDto;
import com.authentication.service.demo.EmployeesService;
import com.authentication.utils.Constants;

/**
 * @author TungBoom
 *
 */
@RestController
@RequestMapping(Constants.API_PATH_PREFIX + "employees")
public class EmployeesAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeesAPI.class);

    @Autowired
    EmployeesService employeesService;
    
    @PostMapping("/search")
    public ResponseEntity<Datatable> search(Principal principal, @RequestBody ObjectUsersDto objectUsersDto) {
        Datatable data = employeesService.search(principal, objectUsersDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/delete")
    public ResponseEntity<ResultDto> delete(Principal principal, Long userId) {
        ResultDto result = employeesService.delete(principal, userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getDetail")
    public ResponseEntity<ObjectUsersDto> getDetail(Principal principal, Long userId) {
    	ObjectUsersDto data = employeesService.getDetail(principal, userId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<ResultDto> add(Principal principal, List<MultipartFile> files, String formDataJson) {
    	ResultDto data = employeesService.add(principal, files, formDataJson);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/edit")
    public ResponseEntity<ResultDto> edit(Principal principal, List<MultipartFile> files, String formDataJson) {
    	ResultDto data = employeesService.edit(principal, files, formDataJson);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/checkExistUsername")
    public ResponseEntity<ResultDto> checkExistUsername(Principal principal, String username, Long userId) {
        ResultDto result = employeesService.checkExistUsername(username, userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/checkExistEmployeeCode")
    public ResponseEntity<ResultDto> checkExistEmployeeCode(Principal principal, String employeeCode, Long userId) {
        ResultDto result = employeesService.checkExistEmployeeCode(employeeCode, userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
