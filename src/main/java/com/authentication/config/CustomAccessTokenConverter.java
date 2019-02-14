package com.authentication.config;

import java.util.Map;

import com.authentication.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {
	
	@Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
	    OAuth2Authentication authentication = super.extractAuthentication(map);
	    CustomOAuth2Request tenantAwareOAuth2Request = new CustomOAuth2Request(authentication.getOAuth2Request());
	    CustomUserDetail customUserDetail = userDetailsServiceImpl.loadUserByUsername(authentication.getName());
	    tenantAwareOAuth2Request.setCustomUserDetail(customUserDetail);
	    return new OAuth2Authentication(tenantAwareOAuth2Request, authentication.getUserAuthentication());
	}
}
