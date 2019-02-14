package com.authentication.service.demo;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.LocationsDto;
import com.authentication.repository.demo.LocationsRepository;

/**
 * @author TungBoom
 *
 */
@Service
public class LocationsServiceImpl implements LocationsService {
    private final Logger LOG = LoggerFactory.getLogger(LocationsServiceImpl.class);

    @Autowired
    protected LocationsRepository locationsRepository;
    
    @Override
    public Datatable search(Principal principal, LocationsDto locationsDto) {
    	LOG.debug("Request to search : {}", locationsDto);
        return locationsRepository.search(principal, locationsDto);
    }
    
    @Override
    public ResultDto delete(Principal principal, Long locationId) {
    	LOG.debug("Request to delete : {}", locationId);
        return locationsRepository.delete(principal, locationId);
    }

    @Override
    public LocationsDto getDetail(Principal principal, Long locationId) {
    	LOG.debug("Request to getDetail : {}", locationId);
        return locationsRepository.getDetail(principal, locationId);
    }
    
    @Override
    public ResultDto add(Principal principal, LocationsDto locationsDto) {
    	LOG.debug("Request to add : {}", locationsDto);
        return locationsRepository.add(principal, locationsDto);
    }
    
    @Override
    public ResultDto edit(Principal principal, LocationsDto locationsDto) {
    	LOG.debug("Request to edit : {}", locationsDto);
        return locationsRepository.edit(principal, locationsDto);
    }
}
