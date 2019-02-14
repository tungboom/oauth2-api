package com.authentication.model.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.authentication.dto.demo.LocationsDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author TungBoom
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "locations")
public class LocationsEntity {
	
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false, updatable = false)
    private Long locationId;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "location_code")
    private String locationCode;

    @Column(name = "parent_location_code")
    private String parentLocationCode;
    
    @Column(name = "status")
    private Long status;
    
    @Column(name = "location_level")
    private Long locationLevel;
    
    public LocationsDto toDto() {
		return new LocationsDto(locationId, locationName, locationCode, parentLocationCode, status, locationLevel);
	}

	public LocationsEntity(Long locationId, String locationName, String locationCode, String parentLocationCode, Long status, Long locationLevel) {
		this.locationId = locationId;
		this.locationName = locationName;
		this.locationCode = locationCode;
		this.parentLocationCode = parentLocationCode;
		this.status = status;
		this.locationLevel = locationLevel;
	}
}
