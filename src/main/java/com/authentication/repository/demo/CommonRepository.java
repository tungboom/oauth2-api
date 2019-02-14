package com.authentication.repository.demo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.authentication.dto.ResultDto;
import com.authentication.dto.TreeNode;
import com.authentication.dto.demo.FilesDto;
import com.authentication.dto.demo.UsersGeolocationDto;
import com.authentication.model.demo.UsersGeolocationEntity;

/**
 * @author TungBoom
 *
 */
@Repository
public interface CommonRepository {

    ResultDto getDBSysDate();
    FilesDto getFileByFileId(Long fileId);
    ResultDto saveUsersGeolocation(UsersGeolocationDto usersGeolocationDto);
    List<UsersGeolocationEntity> getUsersGeolocationByUserId(Long userId);
    List<TreeNode> getTreeLocations(String parentLocationCode);
}
