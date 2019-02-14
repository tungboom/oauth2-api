package com.authentication.dto.demo;

import com.authentication.dto.BaseDto;
import com.authentication.model.demo.LocationsEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author TungBoom
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class LocationsDto extends BaseDto {
	
	private Long locationId;
    private String locationName;
    private String locationCode;
    private String parentUnitCode;
    private Long status;
    private Long locationLevel;
	
	public LocationsEntity toEntity() {
		return new LocationsEntity(locationId, locationName, locationCode, parentUnitCode, status, locationLevel);
	}

	public LocationsDto(Long locationId, String locationName, String locationCode, String parentUnitCode, Long status, Long locationLevel) {
		this.locationId = locationId;
		this.locationName = locationName;
		this.locationCode = locationCode;
		this.parentUnitCode = parentUnitCode;
		this.status = status;
		this.locationLevel = locationLevel;
	}
}
