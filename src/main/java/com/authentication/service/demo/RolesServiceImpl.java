package com.authentication.service.demo;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.RolesDto;
import com.authentication.repository.demo.RolesRepository;

/**
 * @author TungBoom
 *
 */
@Service
public class RolesServiceImpl implements RolesService {
    private final Logger LOG = LoggerFactory.getLogger(RolesServiceImpl.class);

    @Autowired
    protected RolesRepository rolesRepository;
    
    @Override
    public Datatable search(Principal principal, RolesDto rolesDto) {
    	LOG.debug("Request to search : {}", rolesDto);
        return rolesRepository.search(principal, rolesDto);
    }
    
    @Override
    public ResultDto delete(Principal principal, Long roleId) {
    	LOG.debug("Request to delete : {}", roleId);
        return rolesRepository.delete(principal, roleId);
    }

    @Override
    public RolesDto getDetail(Principal principal, Long roleId) {
    	LOG.debug("Request to getDetail : {}", roleId);
        return rolesRepository.getDetail(principal, roleId);
    }
    
    @Override
    public ResultDto add(Principal principal, RolesDto rolesDto) {
    	LOG.debug("Request to add : {}", rolesDto);
        return rolesRepository.add(principal, rolesDto);
    }
    
    @Override
    public ResultDto edit(Principal principal, RolesDto rolesDto) {
    	LOG.debug("Request to edit : {}", rolesDto);
        return rolesRepository.edit(principal, rolesDto);
    }
}
