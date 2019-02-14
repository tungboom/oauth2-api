package com.authentication.api.demo;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.RolesDto;
import com.authentication.service.demo.RolesService;
import com.authentication.utils.Constants;

/**
 * @author TungBoom
 *
 */
@RestController
@RequestMapping(Constants.API_PATH_PREFIX + "roles")
public class RolesAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(RolesAPI.class);

    @Autowired
    RolesService rolesService;
    
    @PostMapping("/search")
    public ResponseEntity<Datatable> search(Principal principal, RolesDto rolesDto) {
        Datatable data = rolesService.search(principal, rolesDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/delete")
    public ResponseEntity<ResultDto> delete(Principal principal, Long userId) {
        ResultDto result = rolesService.delete(principal, userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getDetail")
    public ResponseEntity<RolesDto> getDetail(Principal principal, Long userId) {
    	RolesDto data = rolesService.getDetail(principal, userId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<ResultDto> add(Principal principal, RolesDto rolesDto) {
    	ResultDto data = rolesService.add(principal, rolesDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/edit")
    public ResponseEntity<ResultDto> edit(Principal principal, RolesDto rolesDto) {
    	ResultDto data = rolesService.edit(principal, rolesDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
