package com.authentication.repository.demo;

import java.security.Principal;

import org.springframework.stereotype.Repository;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.LocationsDto;

/**
 * @author TungBoom
 *
 */
@Repository
public interface LocationsRepository {
	
	Datatable search(Principal principal, LocationsDto locationsDto);
    ResultDto delete(Principal principal, Long locationId);
    LocationsDto getDetail(Principal principal, Long locationId);
    ResultDto add(Principal principal, LocationsDto locationsDto);
    ResultDto edit(Principal principal, LocationsDto locationsDto);
}
