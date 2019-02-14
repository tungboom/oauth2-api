package com.authentication.repository.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.authentication.config.CustomOAuth2Request;
import com.authentication.config.CustomUserDetail;
import com.authentication.dto.Datatable;
import com.authentication.dto.ResultDto;
import com.authentication.dto.UsersDto;
import com.authentication.dto.demo.FilesDto;
import com.authentication.dto.demo.ObjectRolesDto;
import com.authentication.dto.demo.ObjectUsersDto;
import com.authentication.dto.demo.PermissionsDto;
import com.authentication.dto.demo.PrivilegesDto;
import com.authentication.dto.demo.RolesDto;
import com.authentication.model.UsersEntity;
import com.authentication.model.demo.EmployeesEntity;
import com.authentication.model.demo.FilesEntity;
import com.authentication.repository.BaseRepository;
import com.authentication.utils.Constants;
import com.authentication.utils.FilesUtils;
import com.authentication.utils.SQLBuilder;
import com.authentication.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import liquibase.util.file.FilenameUtils;;

/**
 * @author TungBoom
 *
 */
@SuppressWarnings("rawtypes")
@Repository
public class EmployeesRepositoryImpl extends BaseRepository implements EmployeesRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeesRepositoryImpl.class);
	
	@Autowired
    private CommonRepository commonRepository;
	
	@Override
    public void saveUserLogin(UsersEntity usersEntity, UsersDto usersDto) {
  	  	try {
  	  		ResultDto resultDto= commonRepository.getDBSysDate();
  	  		usersEntity.setSignInCount(usersEntity.getSignInCount() + 1L);
  	  		usersEntity.setLastSignInAt(usersEntity.getCurrentSignInAt());
  	  		usersEntity.setLastSignInIp(usersEntity.getCurrentSignInIp());
  	  		usersEntity.setCurrentSignInAt(resultDto.getSystemDate());
  	  		usersEntity.setCurrentSignInIp(usersDto.getCurrentSignInIp());
  	  		getEntityManager().merge(usersEntity);
  	  	} catch (Exception ex) {
  	  		LOGGER.error(ex.getMessage(), ex);
  	  	}
    }
	
	@Override
	public RolesDto getRoleByUsername(String username) {
		RolesDto result = new RolesDto();
        try {
        	String sqlQuery = SQLBuilder.getSqlQueryById(SQLBuilder.SQL_MODULE_EMPLOYEES, "get-list-roles-by-username");
        	Map<String, Object> parameters = new HashMap<>();
            parameters.put("username", username);
            List<ObjectRolesDto> objectRolesDtos = getNamedParameterJdbcTemplate().query(sqlQuery, parameters, BeanPropertyRowMapper.newInstance(ObjectRolesDto.class));
            if(!objectRolesDtos.isEmpty()) {
            	result.setRoleId(objectRolesDtos.get(0).getRoleId());
            	result.setRoleCode(objectRolesDtos.get(0).getRoleCode());
            	result.setRoleName(objectRolesDtos.get(0).getRoleName());
            	
            	List<PrivilegesDto> privilegesDtos = new ArrayList<>();
            	for (ObjectRolesDto objectRolesDto : objectRolesDtos) {
            		PrivilegesDto privilegesDto = new PrivilegesDto();
            		privilegesDto.setPrivilegeId(objectRolesDto.getPrivilegeId());
            		privilegesDto.setPrivilegeCode(objectRolesDto.getPrivilegeCode());
            		privilegesDto.setPrivilegeName(objectRolesDto.getPrivilegeName());
            		privilegesDtos.add(privilegesDto);
				}
            	List<PrivilegesDto> privilegesDtosUnique = privilegesDtos.stream().distinct().collect(Collectors.toList());
            	for (PrivilegesDto privilegesDto : privilegesDtosUnique) {
            		List<PermissionsDto> permissionsDtos = new ArrayList<>();
            		for (ObjectRolesDto objectRolesDto : objectRolesDtos) {
            			if(privilegesDto.getPrivilegeId().longValue() == objectRolesDto.getPrivilegeId().longValue()) {
            				PermissionsDto permissionsDto = new PermissionsDto();
                    		permissionsDto.setPermissionId(objectRolesDto.getPermissionId());
                    		permissionsDto.setPermissionCode(objectRolesDto.getPermissionCode());
                    		permissionsDto.setPermissionName(objectRolesDto.getPermissionName());
                    		permissionsDtos.add(permissionsDto);
            			}
					}
            		privilegesDto.setListPermissionsDto(permissionsDtos);
				}
            	result.setListPrivilegesDto(privilegesDtosUnique);
            }
        } catch (Exception ex) {
        	LOGGER.error(ex.getMessage(), ex);
        }
        return result;
	}
	
	@Override
    public ObjectUsersDto getDetailUserByUsername(String username) {
        ObjectUsersDto data = new ObjectUsersDto();
        try {
            ResourceBundle resource = ResourceBundle.getBundle("application"); 
            String uploadFolder = resource.getString("config.folder.upload");
            String sqlQuery = SQLBuilder.getSqlQueryById(SQLBuilder.SQL_MODULE_EMPLOYEES, "get-data-detail-user-by-username");
        	Map<String, Object> parameters = new HashMap<>();
            parameters.put("username", username);
            List<ObjectUsersDto> list = getNamedParameterJdbcTemplate().query(sqlQuery, parameters, BeanPropertyRowMapper.newInstance(ObjectUsersDto.class));
            if (!list.isEmpty()){
                data = list.get(0);
                if(data.getAvatarId() != null) {
                    String fileExtension = FilenameUtils.getExtension(data.getAvatarName());
                    InputStream is = new FileInputStream(uploadFolder + File.separator + data.getAvatarPath());
                    byte[] contents = IOUtils.toByteArray(is);
                    String base64 = Base64.encodeBase64String(contents);
                    data.setAvatarType("image/" + fileExtension);
                    data.setAvatarBase64("data:image/" + fileExtension + ";base64," + base64);
                }
            }
        } catch (Exception ex) {
        	LOGGER.error(ex.getMessage(), ex);
        }
        return data;
    }

	@SuppressWarnings("unchecked")
	@Override
    public Datatable search(Principal principal, ObjectUsersDto objectUsersDto) {
        Datatable dataReturn = new Datatable();
        try {
        	CustomOAuth2Request oAuth2Request = (CustomOAuth2Request) ((OAuth2Authentication) principal).getOAuth2Request();
            CustomUserDetail customUserDetail = oAuth2Request.getCustomUserDetail();
            
        	String sqlQuery = SQLBuilder.getSqlQueryById(SQLBuilder.SQL_MODULE_EMPLOYEES, "get-data-table-user");
        	
            if (StringUtils.isNotNullOrEmpty(objectUsersDto.getFullName())){
            	sqlQuery += " AND (LOWER(emp.first_name || emp.last_name) LIKE :fullName)";
            }
            if (StringUtils.isNotNullOrEmpty(objectUsersDto.getUsername())){
            	sqlQuery += " AND (LOWER(us.username) LIKE :username)";
            }
            if (StringUtils.isNotNullOrEmpty(objectUsersDto.getEmail())){
            	sqlQuery += " AND (LOWER(emp.email) LIKE :email)";
            }
            if (StringUtils.isNotNullOrEmpty(objectUsersDto.getEmployeeCode())){
            	sqlQuery += " AND (LOWER(emp.employee_code) LIKE :employeeCode)";
            }
            if (StringUtils.isNotNullOrEmpty(objectUsersDto.getCreatedUser())){
            	sqlQuery += " AND (LOWER(us.created_user) LIKE :createdUser)";
            }
            if (StringUtils.isNotNullOrEmpty(objectUsersDto.getEnabledString())){
            	sqlQuery += " AND us.enabled = :enabledString";
            }
            
        	Map<String, Object> parameters = new HashMap<>();
        	
            if (StringUtils.isNotNullOrEmpty(objectUsersDto.getFullName())){
            	parameters.put("fullName", StringUtils.convertLowerParamContains(objectUsersDto.getFullName()));
            }
            if (StringUtils.isNotNullOrEmpty(objectUsersDto.getUsername())){
            	parameters.put("username", StringUtils.convertLowerParamContains(objectUsersDto.getUsername()));
            }
            if (StringUtils.isNotNullOrEmpty(objectUsersDto.getEmail())){
            	parameters.put("email", StringUtils.convertLowerParamContains(objectUsersDto.getEmail()));
            }
            if (StringUtils.isNotNullOrEmpty(objectUsersDto.getEmployeeCode())){
            	parameters.put("employeeCode", StringUtils.convertLowerParamContains(objectUsersDto.getEmployeeCode()));
            }
            if (StringUtils.isNotNullOrEmpty(objectUsersDto.getCreatedUser())){
            	parameters.put("createdUser", StringUtils.convertLowerParamContains(objectUsersDto.getCreatedUser()));
            }
            if (StringUtils.isNotNullOrEmpty(objectUsersDto.getEnabledString())){
                if("1".equals(objectUsersDto.getEnabledString())) {
                    parameters.put("enabledString", true);
                } else if("0".equals(objectUsersDto.getEnabledString())) {
                    parameters.put("enabledString", false);
                }
            }
        	
	        List<ObjectUsersDto> list = getListDataBySqlQuery(sqlQuery, parameters,
				objectUsersDto.getPage(), objectUsersDto.getPageSize(),
				ObjectUsersDto.class, true,
				objectUsersDto.getSortName(), objectUsersDto.getSortType());
	        
            int count = 0;
            if(list.isEmpty()) {
            	dataReturn.setTotal(count);
            } else {
            	count = list.get(0).getTotalRow();
            	dataReturn.setTotal(list.get(0).getTotalRow());
			}
			if (objectUsersDto.getPageSize() > 0){
                if (count % objectUsersDto.getPageSize() == 0){
                    dataReturn.setPages(count / objectUsersDto.getPageSize());
                }
                else {
                    dataReturn.setPages((count / objectUsersDto.getPageSize()) + 1);
                }
            }

            dataReturn.setData(list);
        } catch (Exception ex) {
        	LOGGER.error(ex.getMessage(), ex);
        }

        return dataReturn;
    }
    
    @Override
    public ResultDto delete(Principal principal, Long userId) {
        ResultDto resultDTO = new ResultDto();
        try {
            UsersEntity usersEntity = getEntityManager().find(UsersEntity.class, userId);
            if (usersEntity != null){
            	if("ADMIN".equals(usersEntity.getRoleCode())) {
            		resultDTO.setKey(Constants.RESULT.ERROR);
            	} else {
            		usersEntity.setStatus(0L);
                	getEntityManager().persist(usersEntity);
                    resultDTO.setKey(Constants.RESULT.SUCCESS);
            	}
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
    public ObjectUsersDto getDetail(Principal principal, Long userId) {
        ObjectUsersDto data = new ObjectUsersDto();
        try {
            ResourceBundle resource = ResourceBundle.getBundle("application"); 
            String uploadFolder = resource.getString("config.folder.upload");
            
            CustomOAuth2Request oAuth2Request = (CustomOAuth2Request) ((OAuth2Authentication) principal).getOAuth2Request();
            CustomUserDetail customUserDetail = oAuth2Request.getCustomUserDetail();
            
            String sqlQuery = SQLBuilder.getSqlQueryById(SQLBuilder.SQL_MODULE_EMPLOYEES, "get-data-detail-user-by-userId");
            if(!Constants.ROLE.ADMIN.equals(customUserDetail.getUsersEntity().getRoleCode())) {
                sqlQuery = sqlQuery + " AND us.created_user = :usernameLogin ";
            }
        	Map<String, Object> parameters = new HashMap<>();
            parameters.put("userId", userId);
            if(!Constants.ROLE.ADMIN.equals(customUserDetail.getUsersEntity().getRoleCode())) {
                parameters.put("usernameLogin", customUserDetail.getUsersEntity().getUsername());
            }
            List<ObjectUsersDto> list = getNamedParameterJdbcTemplate().query(sqlQuery, parameters, BeanPropertyRowMapper.newInstance(ObjectUsersDto.class));
            if (!list.isEmpty()){
                data = list.get(0);
                if(data.getAvatarId() != null) {
                    String fileExtension = FilenameUtils.getExtension(data.getAvatarName());
                    InputStream is = new FileInputStream(uploadFolder + File.separator + data.getAvatarPath());
                    byte[] contents = IOUtils.toByteArray(is);
                    String base64 = Base64.encodeBase64String(contents);
                    data.setAvatarType("image/" + fileExtension);
                    data.setAvatarBase64("data:image/" + fileExtension + ";base64," + base64);
                }
            }
        } catch (Exception ex) {
        	LOGGER.error(ex.getMessage(), ex);
        }
        return data;
    }
    
	@Override
    public ResultDto add(Principal principal, List<MultipartFile> files, String formDataJson) {
        ResultDto resultDTO = new ResultDto();
        resultDTO.setKey(Constants.RESULT.SUCCESS);
        String uploadFolder = "";
        String pathFileAvatar = "";
        try {
            ResourceBundle resource = ResourceBundle.getBundle("application"); 
            uploadFolder = resource.getString("config.folder.upload");
            CustomOAuth2Request oAuth2Request = (CustomOAuth2Request) ((OAuth2Authentication) principal).getOAuth2Request();
            CustomUserDetail customUserDetail = oAuth2Request.getCustomUserDetail();
            ObjectMapper mapper = new ObjectMapper();
        	ObjectUsersDto objectUsersDto = mapper.readValue(formDataJson, ObjectUsersDto.class);
            resultDTO = validateData(objectUsersDto);
            if (Constants.RESULT.SUCCESS.equals(resultDTO.getKey())){
                ResultDto resultDtoDate = commonRepository.getDBSysDate();
                objectUsersDto.setCreatedTime(resultDtoDate.getSystemDate());
                objectUsersDto.setCreatedUser(customUserDetail.getUsersEntity().getUsername());
                objectUsersDto.setPassword(new BCryptPasswordEncoder().encode(objectUsersDto.getPassword().trim()));
                objectUsersDto.setSignInCount(0L);

                List<FilesDto> listFilesDto = FilesUtils.saveMultipleUploadedFile(files, uploadFolder);
                if(listFilesDto.size() == 1) {
                    FilesEntity filesEntity = listFilesDto.get(0).toEntity();
                    pathFileAvatar = filesEntity.getFilePath();
                    filesEntity.setCreatedTime(objectUsersDto.getCreatedTime());
                    filesEntity.setCreatedUser(objectUsersDto.getCreatedUser());
                    FilesEntity filesEntitySave = getEntityManager().merge(filesEntity);
                    objectUsersDto.setAvatarId(filesEntitySave.getFileId());
                }

                UsersEntity usersEntity = objectUsersDto.toUsersEntity();
                UsersEntity usersEntitySave = getEntityManager().merge(usersEntity);
                objectUsersDto.setUserId(usersEntitySave.getUserId());
                EmployeesEntity employeesEntity = objectUsersDto.toEmployeesEntity();
                getEntityManager().merge(employeesEntity);
                resultDTO.setId(String.valueOf(usersEntitySave.getUserId()));
            }
        } catch (Exception ex) {
            if(!"".equals(pathFileAvatar) && !"".equals(uploadFolder)) {
                FilesUtils.deleteFileByPath(pathFileAvatar, uploadFolder);
            }
            resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage(ex.getMessage());
            LOGGER.error(ex.getMessage(), ex);
        }
        return resultDTO;
    }

    @Override
    public ResultDto edit(Principal principal, List<MultipartFile> files, String formDataJson) {
        ResultDto resultDTO = new ResultDto();
        resultDTO.setKey(Constants.RESULT.SUCCESS);
        String uploadFolder = "";
        String pathFileAvatar = "";
        try {
            ResourceBundle resource = ResourceBundle.getBundle("application");
            uploadFolder = resource.getString("config.folder.upload");
            CustomOAuth2Request oAuth2Request = (CustomOAuth2Request) ((OAuth2Authentication) principal).getOAuth2Request();
            CustomUserDetail customUserDetail = oAuth2Request.getCustomUserDetail();
            ObjectMapper mapper = new ObjectMapper();
        	ObjectUsersDto objectUsersDto = mapper.readValue(formDataJson, ObjectUsersDto.class);
            resultDTO = validateData(objectUsersDto);
            if (Constants.RESULT.SUCCESS.equals(resultDTO.getKey())){
                ResultDto resultDto= commonRepository.getDBSysDate();
                objectUsersDto.setUpdatedTime(resultDto.getSystemDate());
                objectUsersDto.setUpdatedUser(customUserDetail.getUsersEntity().getUsername());

                if(objectUsersDto.getAvatarId() != null) {
                	//Delete file old
                    FilesEntity filesEntityOld = getEntityManager().find(FilesEntity.class, objectUsersDto.getAvatarId());
                    if(filesEntityOld != null) {
                        FilesUtils.deleteFileByPath(filesEntityOld.getFilePath(), uploadFolder);
                        getEntityManager().remove(filesEntityOld);
                    }
                }
                //Upload file new
                List<FilesDto> listFilesDto = FilesUtils.saveMultipleUploadedFile(files, uploadFolder);
                if(listFilesDto.size() == 1) {
                    FilesEntity filesEntityNew = listFilesDto.get(0).toEntity();
                    pathFileAvatar = filesEntityNew.getFilePath();
                    filesEntityNew.setCreatedTime(objectUsersDto.getCreatedTime());
                    filesEntityNew.setCreatedUser(objectUsersDto.getCreatedUser());
                    FilesEntity filesEntitySave = getEntityManager().merge(filesEntityNew);
                    objectUsersDto.setAvatarId(filesEntitySave.getFileId());
                }

                UsersEntity usersEntityOld = getEntityManager().find(UsersEntity.class, objectUsersDto.getUserId());
                UsersEntity usersEntityNew = toUpdateUsersEntity(usersEntityOld, objectUsersDto);
                getEntityManager().persist(usersEntityNew);
                EmployeesEntity employeesEntityOld = getEntityManager().find(EmployeesEntity.class, objectUsersDto.getEmployeeId());
                EmployeesEntity employeesEntityNew = toUpdateEmployeesEntity(employeesEntityOld, objectUsersDto);
                getEntityManager().persist(employeesEntityNew);
            }
        } catch (Exception ex) {
            if(!"".equals(pathFileAvatar) && !"".equals(uploadFolder)) {
                FilesUtils.deleteFileByPath(pathFileAvatar, uploadFolder);
            }
            resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage(ex.getMessage());
            LOGGER.error(ex.getMessage(), ex);
        }
        return resultDTO;
    }

	public ResultDto validateData(ObjectUsersDto objectUsersDto) {
        ResultDto resultDTO = new ResultDto();
        resultDTO.setKey(Constants.RESULT.SUCCESS);
        
        if (StringUtils.isStringNullOrEmpty(objectUsersDto.getUsername())){
            resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage("userName is null");
            return resultDTO;
        }
        
        if (StringUtils.isStringNullOrEmpty(objectUsersDto.getFirstName())){
            resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage("firstName is null");
            return resultDTO;
        }
        
        if (StringUtils.isStringNullOrEmpty(objectUsersDto.getLastName())){
            resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage("lastName is null");
            return resultDTO;
        }
        
        if (StringUtils.isStringNullOrEmpty(objectUsersDto.getEmail())){
            resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage("email is null");
            return resultDTO;
        }
        
        if (StringUtils.isStringNullOrEmpty(objectUsersDto.getEmployeeCode())){
            resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage("employeeCode is null");
            return resultDTO;
        }
        
        ResultDto checkUsername = checkExistUsername(objectUsersDto.getUsername(), objectUsersDto.getUserId());
        if (Constants.RESULT.DUPLICATE.equals(checkUsername.getKey())) {
    		resultDTO.setKey(Constants.RESULT.DUPLICATE);
            resultDTO.setMessage("USERNAME");
            return resultDTO;
    	}
        
        ResultDto checkEmployeeCode = checkExistUsername(objectUsersDto.getEmployeeCode(), objectUsersDto.getUserId());
        if (Constants.RESULT.DUPLICATE.equals(checkEmployeeCode.getKey())) {
    		resultDTO.setKey(Constants.RESULT.DUPLICATE);
            resultDTO.setMessage("EMPLOYEECODE");
            return resultDTO;
    	}
        
        return resultDTO;
    }

    public EmployeesEntity toUpdateEmployeesEntity(EmployeesEntity employeesEntity, ObjectUsersDto objectUsersDto) {
        // employeesEntity.setEmployeeId(objectUsersDto.getEmployeeId());
		employeesEntity.setEmployeeCode(objectUsersDto.getEmployeeCode());
		employeesEntity.setFirstName(objectUsersDto.getFirstName());
		employeesEntity.setLastName(objectUsersDto.getLastName());
		employeesEntity.setEmail(objectUsersDto.getEmail());
		employeesEntity.setPhone(objectUsersDto.getPhone());
		employeesEntity.setDateOfBirth(objectUsersDto.getDateOfBirth());
        employeesEntity.setUnitId(objectUsersDto.getUnitId());
        employeesEntity.setAvatarId(objectUsersDto.getAvatarId());
		// employeesEntity.setUserId(objectUsersDto.getUserId());
        return employeesEntity;
    }

    public UsersEntity toUpdateUsersEntity(UsersEntity usersEntity, ObjectUsersDto objectUsersDto) {
        // usersEntity.setUserId(objectUsersDto.getUserId());
        usersEntity.setUsername(objectUsersDto.getUsername());
        if(StringUtils.isNotNullOrEmpty(objectUsersDto.getPassword())) {
            usersEntity.setPassword(new BCryptPasswordEncoder().encode(objectUsersDto.getPassword().trim()));
        }
		usersEntity.setEnabled(objectUsersDto.isEnabled());
		// usersEntity.setRoleCode(objectUsersDto.getRoleCode());
		// usersEntity.setCreatedTime(objectUsersDto.getCreatedTime());
		// usersEntity.setCreatedUser(objectUsersDto.getCreatedUser());
		usersEntity.setUpdatedTime(objectUsersDto.getUpdatedTime());
		usersEntity.setUpdatedUser(objectUsersDto.getUpdatedUser());
		// usersEntity.setSignInCount(objectUsersDto.getSignInCount());
		// usersEntity.setCurrentSignInAt(objectUsersDto.getCurrentSignInAt());
		// usersEntity.setCurrentSignInIp(objectUsersDto.getCurrentSignInIp());
		// usersEntity.setCurrentSignInMac(objectUsersDto.getCurrentSignInMac());
		// usersEntity.setLastSignInAt(objectUsersDto.getLastSignInAt());
		// usersEntity.setLastSignInIp(objectUsersDto.getLastSignInIp());
		// usersEntity.setLastSignInMac(objectUsersDto.getLastSignInMac());
        return usersEntity;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public ResultDto checkExistUsername(String username, Long userId) {
        ResultDto resultDTO = new ResultDto();
        resultDTO.setKey(Constants.RESULT.SUCCESS);
        try {
            List<UsersEntity> usersEntities = findByMultilParam(UsersEntity.class, "username", username);
            if (!usersEntities.isEmpty()){
            	if(userId != null) {
            		UsersEntity usersEntity = getEntityManager().find(UsersEntity.class, userId);
            		if(!username.equals(usersEntity.getUsername())) {
            			resultDTO.setKey(Constants.RESULT.DUPLICATE);
            		}
            	} else {
            		resultDTO.setKey(Constants.RESULT.DUPLICATE);
            	}
            }
        } catch (Exception ex) {
            resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage(ex.getMessage());
            LOGGER.error(ex.getMessage(), ex);
        }
        return resultDTO;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public ResultDto checkExistEmployeeCode(String employeeCode, Long userId) {
        ResultDto resultDTO = new ResultDto();
        resultDTO.setKey(Constants.RESULT.SUCCESS);
        try {
        	List<EmployeesEntity> employeesEntities = findByMultilParam(EmployeesEntity.class, "employeeCode", employeeCode);
        	if (!employeesEntities.isEmpty()){
            	if(userId != null) {
            		List<EmployeesEntity> employeesEntitiesUserId = findByMultilParam(EmployeesEntity.class, "userId", userId);
            		if(!employeesEntitiesUserId.isEmpty()) {
            			if(!employeeCode.equals(employeesEntitiesUserId.get(0).getEmployeeCode())) {
                			resultDTO.setKey(Constants.RESULT.DUPLICATE);
                		}
            		}
            	} else {
            		resultDTO.setKey(Constants.RESULT.DUPLICATE);
            	}
            }
        } catch (Exception ex) {
            resultDTO.setKey(Constants.RESULT.ERROR);
            resultDTO.setMessage(ex.getMessage());
            LOGGER.error(ex.getMessage(), ex);
        }
        return resultDTO;
    }
}
