package com.authentication.api.demo;

import java.security.Principal;

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

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.LocationsDto;
import com.authentication.service.demo.LocationsService;
import com.authentication.utils.Constants;

/**
 * @author TungBoom
 *
 */
@RestController
@RequestMapping(Constants.API_PATH_PREFIX + "locations")
public class LocationsAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationsAPI.class);

    @Autowired
    LocationsService locationsService;
    
    @PostMapping("/search")
    public ResponseEntity<Datatable> search(Principal principal, @RequestBody LocationsDto locationsDto) {
        Datatable data = locationsService.search(principal, locationsDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/delete")
    public ResponseEntity<ResultDto> delete(Principal principal, Long locationId) {
        ResultDto result = locationsService.delete(principal, locationId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getDetail")
    public ResponseEntity<LocationsDto> getDetail(Principal principal, Long locationId) {
    	LocationsDto data = locationsService.getDetail(principal, locationId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<ResultDto> add(Principal principal, @RequestBody LocationsDto locationsDto) {
    	ResultDto data = locationsService.add(principal, locationsDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PostMapping("/edit")
    public ResponseEntity<ResultDto> edit(Principal principal, @RequestBody LocationsDto locationsDto) {
    	ResultDto data = locationsService.edit(principal, locationsDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
