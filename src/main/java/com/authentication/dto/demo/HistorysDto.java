package com.authentication.dto.demo;

import java.util.List;

import com.authentication.dto.BaseDto;
import com.authentication.model.demo.PrivilegesEntity;

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
public class HistorysDto extends BaseDto {
	
	private Long privilegeId;
    private String privilegeCode;
    private String privilegeName;
    private Long status;
    private String description;
    List<PermissionsDto> listPermissionsDto;
    
    public PrivilegesEntity toEntity() {
		return new PrivilegesEntity(privilegeId, privilegeCode, privilegeName, status, description);
	}

	public HistorysDto(Long privilegeId, String privilegeCode, String privilegeName, Long status, String description) {
		this.privilegeId = privilegeId;
		this.privilegeCode = privilegeCode;
		this.privilegeName = privilegeName;
		this.status = status;
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((privilegeId == null) ? 0 : privilegeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistorysDto other = (HistorysDto) obj;
		if (privilegeId == null) {
			if (other.privilegeId != null)
				return false;
		} else if (!privilegeId.equals(other.privilegeId))
			return false;
		return true;
	}
}
