package com.authentication.dto.demo;

import com.authentication.model.demo.UnitsEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author TungBoom
 */
@Getter
@Setter
@NoArgsConstructor
public class UnitDto {
	
    private Long unitId;
    private String unitName;
    private String unitCode;
    private Long parentUnitId;
    private Long status;
    private Long unitLevel;
	
	public UnitsEntity toEntity() {
		return new UnitsEntity(unitId, unitName, unitCode, parentUnitId, status, unitLevel);
	}

	public UnitDto(Long unitId, String unitName, String unitCode, Long parentUnitId, Long status, Long unitLevel) {
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitCode = unitCode;
		this.parentUnitId = parentUnitId;
		this.status = status;
		this.unitLevel = unitLevel;
	}
}
