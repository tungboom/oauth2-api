package com.authentication.service.demo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.ResultDto;
import com.authentication.dto.TreeNode;
import com.authentication.dto.demo.FilesDto;
import com.authentication.dto.demo.UsersGeolocationDto;
import com.authentication.model.demo.UsersGeolocationEntity;
import com.authentication.repository.demo.CommonRepository;

/**
 * @author TungBoom
 *
 */
@Service
public class CommonServiceImpl implements CommonService {
    private final Logger LOG = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    protected CommonRepository commonRepository;

    @Override
    public ResultDto getDBSysDate() {
    	LOG.debug("Request to getDBSysDate : {}");
        return commonRepository.getDBSysDate();
    }
    
    @Override
    public FilesDto getFileByFileId(Long fileId) {
    	LOG.debug("Request to getFileByFileId : " + fileId);
        return commonRepository.getFileByFileId(fileId);
    }
    
    @Override
    public ResultDto saveUsersGeolocation(UsersGeolocationDto usersGeolocationDto) {
    	LOG.debug("Request to saveUsersGeolocation : {}");
    	ResultDto resultDto = commonRepository.getDBSysDate();
    	usersGeolocationDto.setCreatedTime(resultDto.getSystemDate());
        return commonRepository.saveUsersGeolocation(usersGeolocationDto);
    }
    
    @Override
    public List<UsersGeolocationEntity> getUsersGeolocationByUserId(Long userId) {
    	LOG.debug("Request to getUsersGeolocationByUserId : " + userId);
        return commonRepository.getUsersGeolocationByUserId(userId);
    }

    @Override
    public List<TreeNode> getTreeLocations(String parentLocationCode) {
    	LOG.debug("Request to getTreeLocations : " + parentLocationCode);
        return commonRepository.getTreeLocations(parentLocationCode);
    }
}
