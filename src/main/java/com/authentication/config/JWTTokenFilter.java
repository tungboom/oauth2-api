package com.authentication.config;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.authentication.model.UserTokenSessionEntity;
import com.authentication.service.UserTokenSessionService;
import com.authentication.service.UserTokenSessionServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @author TungBoom
 */
public class JWTTokenFilter extends GenericFilterBean {

    @Value("${config.oauth2.tokenTimeout}")
    private long tokenExpiryTime;

    private UserTokenSessionServiceImpl userTokenSessionService;

    public JWTTokenFilter(UserTokenSessionServiceImpl userTokenSessionService) {
        this.userTokenSessionService = userTokenSessionService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String pathLogin = httpServletRequest.getServletPath();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !"/oauth/login".equals(pathLogin) && !"/oauth/logout".equals(pathLogin)) {
            OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) ((OAuth2Authentication) authentication).getDetails();
            String tokenValue = oAuth2AuthenticationDetails.getTokenValue();
            String username = authentication.getName();
            String [] sessionId = new String[1];
            if (Objects.nonNull(httpServletRequest.getCookies())) {
                sessionId = httpServletRequest.getCookies()[0].getName().split(";");
            }else {
                /**
                 * Swagger2 does not support cookie for that putting default sessssion id.
                 */
                sessionId[0] = "JSEESION-ID";
            }
            UserTokenSessionEntity userTokenSession = new UserTokenSessionEntity(username, tokenValue, sessionId[0], tokenExpiryTime);
            httpServletResponse.addHeader("Set-Cookie", userTokenSession.getSessionId());
            try {
                UserTokenSessionService.ValidMappingResponse validMappingResponse = userTokenSessionService.isValidUserTokenSessionMapping(userTokenSession);
                if (!validMappingResponse.isValid()) {
                    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "expired_token");
                }
            } catch (UsernameNotFoundException e) {
                System.out.println(e.getMessage());
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "expired_token");
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
