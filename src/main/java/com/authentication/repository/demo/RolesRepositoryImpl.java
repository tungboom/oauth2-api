package com.authentication.repository.demo;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.authentication.config.CustomOAuth2Request;
import com.authentication.config.CustomUserDetail;
import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.demo.RolesDto;
import com.authentication.model.UsersEntity;
import com.authentication.repository.BaseRepository;
import com.authentication.utils.Constants;
import com.authentication.utils.SQLBuilder;

/**
 * @author TungBoom
 *
 */
@SuppressWarnings("rawtypes")
@Repository
@Transactional
public class RolesRepositoryImpl extends BaseRepository implements RolesRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(RolesRepositoryImpl.class);
	
	@Autowired
    private CommonRepository commonRepository;
	
	@SuppressWarnings("unchecked")
	@Override
    public Datatable search(Principal principal, RolesDto RolesDto) {
        Datatable dataReturn = new Datatable();
        try {
        	CustomOAuth2Request oAuth2Request = (CustomOAuth2Request) ((OAuth2Authentication) principal).getOAuth2Request();
            CustomUserDetail customUserDetail = oAuth2Request.getCustomUserDetail();
    		
        	String sqlQuery = SQLBuilder.getSqlQueryById(SQLBuilder.SQL_MODULE_ROLES, "get-data-table-role");
        	
            
        	Map<String, Object> parameters = new HashMap<>();
        	
	        List<RolesDto> list = getListDataBySqlQuery(sqlQuery, parameters,
				RolesDto.getPage(), RolesDto.getPageSize(),
				RolesDto.class, true,
				RolesDto.getSortName(), RolesDto.getSortType());
	        
            int count = 0;
            if(list.isEmpty()) {
            	dataReturn.setPages(count);
            } else {
            	count = list.get(0).getTotalRow();
            	dataReturn.setPages(list.get(0).getTotalRow());
            }

            dataReturn.setData(list);
        } catch (Exception ex) {
        	LOGGER.error(ex.getMessage(), ex);
        }

        return dataReturn;
    }
    
    @Override
    public ResultDto delete(Principal principal, Long roleId) {
        ResultDto resultDTO = new ResultDto();
        try {
            UsersEntity e = getEntityManager().find(UsersEntity.class, roleId);
            if (e != null){
            	getEntityManager().remove(e);
                resultDTO.setKey(Constants.RESULT.SUCCESS);
            } else{
                resultDTO.setKey(Constants.RESULT.NODATA);
            }
        }
        catch (Exception ex) {
            resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage(ex.getMessage());
            LOGGER.error(ex.getMessage(), ex);
        }
        return resultDTO;
    }
    
    @Override
    public RolesDto getDetail(Principal principal, Long roleId) {
        RolesDto data = new RolesDto();
        try {
        	CustomOAuth2Request oAuth2Request = (CustomOAuth2Request) ((OAuth2Authentication) principal).getOAuth2Request();
            CustomUserDetail customUserDetail = oAuth2Request.getCustomUserDetail();

        	String sqlQuery = SQLBuilder.getSqlQueryById(SQLBuilder.SQL_MODULE_ROLES, "get-data-detail-role-by-roleId");
        	
        	Map<String, Object> parameters = new HashMap<>();
            parameters.put("roleId", roleId);
            List<RolesDto> list = getNamedParameterJdbcTemplate().query(sqlQuery, parameters, BeanPropertyRowMapper.newInstance(RolesDto.class));

            if (list != null && list.size() > 0){
                data = list.get(0);
            }
        } catch (Exception ex) {
        	LOGGER.error(ex.getMessage(), ex);
        }
        return data;
    }
    
	@Override
    public ResultDto add(Principal principal, RolesDto rolesDto) {
        ResultDto resultDTO = new ResultDto();
        resultDTO.setKey(Constants.RESULT.SUCCESS);
        try {
        	CustomOAuth2Request oAuth2Request = (CustomOAuth2Request) ((OAuth2Authentication) principal).getOAuth2Request();
            CustomUserDetail customUserDetail = oAuth2Request.getCustomUserDetail();
            resultDTO = validateData(rolesDto);
            if (Constants.RESULT.SUCCESS.equals(resultDTO.getKey())){
                
            }
        } catch (Exception ex) {
            resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage(ex.getMessage());
            LOGGER.error(ex.getMessage(), ex);
        }
        return resultDTO;
    }
	
	@Override
    public ResultDto edit(Principal principal, RolesDto rolesDto) {
        ResultDto resultDTO = new ResultDto();
        resultDTO.setKey(Constants.RESULT.SUCCESS);
        try {
        	CustomOAuth2Request oAuth2Request = (CustomOAuth2Request) ((OAuth2Authentication) principal).getOAuth2Request();
            CustomUserDetail customUserDetail = oAuth2Request.getCustomUserDetail();
            resultDTO = validateData(rolesDto);
            if (Constants.RESULT.SUCCESS.equals(resultDTO.getKey())){
                
            }
        } catch (Exception ex) {
            resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage(ex.getMessage());
            LOGGER.error(ex.getMessage(), ex);
        }
        return resultDTO;
    }

	public ResultDto validateData(RolesDto rolesDto) {
        ResultDto resultDTO = new ResultDto();
        resultDTO.setKey(Constants.RESULT.SUCCESS);
        
        return resultDTO;
    }
    
}
