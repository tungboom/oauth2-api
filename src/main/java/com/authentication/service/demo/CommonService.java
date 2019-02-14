package com.authentication.service.demo;

import java.util.List;

import com.authentication.dto.ResultDto;
import com.authentication.dto.TreeNode;
import com.authentication.dto.demo.FilesDto;
import com.authentication.dto.demo.UsersGeolocationDto;
import com.authentication.model.demo.UsersGeolocationEntity;

/**
 * @author TungBoom
 *
 */
public interface CommonService {
	ResultDto getDBSysDate();
	FilesDto getFileByFileId(Long fileId);
	ResultDto saveUsersGeolocation(UsersGeolocationDto usersGeolocationDto);
	List<UsersGeolocationEntity> getUsersGeolocationByUserId(Long userId);
	List<TreeNode> getTreeLocations(String parentLocationCode);
}
