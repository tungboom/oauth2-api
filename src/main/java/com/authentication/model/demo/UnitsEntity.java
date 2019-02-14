package com.authentication.model.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.authentication.dto.demo.UnitDto;

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
@Table(name = "units")
public class UnitsEntity {
	
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "unit_id", nullable = false, updatable = false)
    private Long unitId;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "unit_code")
    private String unitCode;

    @Column(name = "parent_unit_id")
    private Long parentUnitId;
    
    @Column(name = "status")
    private Long status;
    
    @Column(name = "unit_level")
    private Long unitLevel;
    
    public UnitDto toDto() {
		return new UnitDto(unitId, unitName, unitCode, parentUnitId, status, unitLevel);
	}

	public UnitsEntity(Long unitId, String unitName, String unitCode, Long parentUnitId, Long status, Long unitLevel) {
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitCode = unitCode;
		this.parentUnitId = parentUnitId;
		this.status = status;
		this.unitLevel = unitLevel;
	}
}
