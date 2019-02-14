package com.authentication.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authentication.config.CustomUserDetail;
import com.authentication.dto.UsersDto;
import com.authentication.dto.demo.RolesDto;
import com.authentication.model.UsersEntity;
import com.authentication.repository.UserRepository;
import com.authentication.repository.demo.EmployeesRepository;
import com.authentication.utils.StringUtils;

/**
 * @author TungBoom
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOGGER = Logger.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private EmployeesRepository employeesRepository;

	@Override
	public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUserDetail customUserDetail = new CustomUserDetail();
	    UsersEntity userFromDataBase = userRepository.findOneByUsername(username);
	    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (userFromDataBase == null) {
			LOGGER.info("User " + username + " was not found in the database");
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        } else {
			if (StringUtils.isNotNullOrEmpty(userFromDataBase.getRoleCode())) {
				grantedAuthorities.add(new SimpleGrantedAuthority(userFromDataBase.getRoleCode()));
				if (!"ADMIN".equals(userFromDataBase.getRoleCode())) {
					RolesDto rolesDto = employeesRepository.getRoleByUsername(username);
					if (rolesDto != null) {
						customUserDetail.setRolesDto(rolesDto);
					}
				}
			} else {
				grantedAuthorities.add(new SimpleGrantedAuthority("NO_ROLE"));
			}
			customUserDetail.setUsersEntity(userFromDataBase);
			customUserDetail.setAuthorities(grantedAuthorities);
			customUserDetail.setAccountNonLocked(true);
			customUserDetail.setAccountNonExpired(true);
			customUserDetail.setCredentialsNonExpired(true);
        }
        return customUserDetail;
	}

	public void saveUserLogin(String username, UsersDto usersDto) {
		UsersEntity userFromDataBase = userRepository.findOneByUsername(username);
		employeesRepository.saveUserLogin(userFromDataBase, usersDto);
	}
}