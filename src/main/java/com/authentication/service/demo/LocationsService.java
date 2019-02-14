package com.authentication.service.demo;

import java.security.Principal;

import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.LocationsDto;

/**
 * @author TungBoom
 *
 */
public interface LocationsService {

	Datatable search(Principal principal, LocationsDto locationsDto);
    ResultDto delete(Principal principal, Long locationId);
    LocationsDto getDetail(Principal principal, Long locationId);
    ResultDto add(Principal principal, LocationsDto locationsDto);
    ResultDto edit(Principal principal, LocationsDto locationsDto);
}
