package com.authentication.repository.demo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.authentication.dto.ResultDto;
import com.authentication.dto.TreeNode;
import com.authentication.dto.demo.FilesDto;
import com.authentication.dto.demo.UsersGeolocationDto;
import com.authentication.model.demo.FilesEntity;
import com.authentication.model.demo.LocationsEntity;
import com.authentication.model.demo.UsersGeolocationEntity;
import com.authentication.repository.BaseRepository;
import com.authentication.utils.Constants;

/**
 * @author TungBoom
 *
 */
@SuppressWarnings("rawtypes")
@Repository
@Transactional
public class CommonRepositoryImpl extends BaseRepository implements CommonRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonRepositoryImpl.class);
	
    @Override
    public ResultDto getDBSysDate() {
        ResultDto data = new ResultDto();
        try {
            String strQuery = "SELECT sysdate() systemDate FROM DUAL";

            List<ResultDto> list = getNamedParameterJdbcTemplate().query(strQuery, BeanPropertyRowMapper.newInstance(ResultDto.class));

            if (list != null && list.size() > 0){
                data = list.get(0);
            }
        } catch (Exception e){
        	LOGGER.error(e.getMessage(), e);
        }
        return data;
    }

    @Override
    public FilesDto getFileByFileId(Long fileId) {
        FilesDto data = new FilesDto();
        try {
            FilesEntity filesEntity = getEntityManager().find(FilesEntity.class, fileId);
            if(filesEntity != null) {
                data = filesEntity.toDto();
            }
        } catch (Exception e){
        	LOGGER.error(e.getMessage(), e);
        }
        return data;
    }
    
    @Override
    public ResultDto saveUsersGeolocation(UsersGeolocationDto usersGeolocationDto) {
    	ResultDto resultDTO = new ResultDto();
        resultDTO.setKey(Constants.RESULT.SUCCESS);
        try {
        	getEntityManager().merge(usersGeolocationDto.toEntity());
        } catch (Exception ex){
        	resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage(ex.getMessage());
        	LOGGER.error(ex.getMessage(), ex);
        }
        return resultDTO;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<UsersGeolocationEntity> getUsersGeolocationByUserId(Long userId) {
    	List<UsersGeolocationEntity> data = new ArrayList<>();
        try {
        	data = findByMultilParam(UsersGeolocationEntity.class, "userId", userId);
        } catch (Exception e){
        	LOGGER.error(e.getMessage(), e);
        }
        return data;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<TreeNode> getTreeLocations(String parentLocationCode) {
    	List<TreeNode> data = new ArrayList<>();
        try {
        	List<LocationsEntity> locationsEntities = findByMultilParam(LocationsEntity.class, "parentLocationCode", parentLocationCode);
        	for (LocationsEntity locationsEntity : locationsEntities) {
        		TreeNode treeNode = new TreeNode();
        		treeNode.setKey(locationsEntity.getLocationCode());
        		treeNode.setTitle(locationsEntity.getLocationName());
        		treeNode.setIsLeaf(false);
        		treeNode.setDisabled(false);
        		treeNode.setDisableCheckbox(false);
        		data.add(treeNode);
			}
        } catch (Exception e){
        	LOGGER.error(e.getMessage(), e);
        }
        return data;
    }
}
