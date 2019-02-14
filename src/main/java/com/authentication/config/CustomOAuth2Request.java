package com.authentication.config;

import org.springframework.security.oauth2.provider.OAuth2Request;

public class CustomOAuth2Request extends OAuth2Request {

	static final long serialVersionUID = 1L;

	public CustomOAuth2Request(OAuth2Request other) {
		super(other);
	}

	private CustomUserDetail customUserDetail;

	public CustomUserDetail getCustomUserDetail() {
		return customUserDetail;
	}

	public void setCustomUserDetail(CustomUserDetail customUserDetail) {
		this.customUserDetail = customUserDetail;
	}
}
