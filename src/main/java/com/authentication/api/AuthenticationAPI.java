package com.authentication.api;

import java.security.Principal;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.dto.UserTokenSessionDto;
import com.authentication.dto.UsersDto;
import com.authentication.dto.demo.ObjectUsersDto;
import com.authentication.model.UserTokenSessionEntity;
import com.authentication.service.UserDetailsServiceImpl;
import com.authentication.service.UserTokenSessionService;
import com.authentication.service.UserTokenSessionServiceImpl;
import com.authentication.service.demo.EmployeesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author TungBoom
 */
@RestController
@RequestMapping("/oauth")
@Api(value="Authentication API", description="Authenticate user using authorization token.")
public class AuthenticationAPI {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationAPI.class);

    @Value("${config.oauth2.tokenTimeout}")
    private long tokenExpiryTime;

    @Autowired
    private UserTokenSessionServiceImpl userTokenSessionService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    
    @Autowired
    EmployeesService employeesService;

    @ApiOperation(value = "Authenticated User Login", response = UserTokenSessionEntity.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserTokenSessionEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<UserTokenSessionDto> login(@RequestHeader HttpHeaders httpHeaders, Principal principal, @RequestBody UsersDto usersDto) {

        String username = principal.getName();
        UserTokenSessionEntity userTokenSession = buildUserTokenSession(principal, httpHeaders);
        userTokenSession = userTokenSessionService.saveUserTokenSessionMapping(userTokenSession);

        LOGGER.info("User "+username+" successfully logged in. User, Token and Session mapping stored."+userTokenSession);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Message", "Success");
        responseHeaders.add("Set-Cookie", userTokenSession.getSessionId());

        userDetailsServiceImpl.saveUserLogin(username, usersDto);
        
        UserTokenSessionDto userTokenSessionDto = new UserTokenSessionDto();
        userTokenSessionDto.setId(userTokenSession.getId());
        userTokenSessionDto.setUsername(userTokenSession.getUsername());
        userTokenSessionDto.setToken(userTokenSession.getToken());
        userTokenSessionDto.setSessionId(userTokenSession.getSessionId());
        userTokenSessionDto.setExpiryTime(userTokenSession.getExpiryTime());
        userTokenSessionDto.setCreatedTime(userTokenSession.getCreatedTime());
        userTokenSessionDto.setUpdatedTime(userTokenSession.getUpdatedTime());
        
        ObjectUsersDto objectUsersDto = employeesService.getDetailUserByUsername(username);
        userTokenSessionDto.setObjectUsersDto(objectUsersDto);

        return new ResponseEntity<UserTokenSessionDto>(userTokenSessionDto, responseHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "Validate the given token", response = UserTokenSessionEntity.class)
    @RequestMapping(value = "/validateToken", method = RequestMethod.POST,
             produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserTokenSessionEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<UserTokenSessionEntity> validateToken(@RequestHeader HttpHeaders httpHeaders,  Principal principal) {


        String username = principal.getName();
        UserTokenSessionEntity userTokenSession = buildUserTokenSession(principal, httpHeaders);

        ResponseEntity<UserTokenSessionEntity> responseEntity;
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Set-Cookie", userTokenSession.getSessionId());

        UserTokenSessionService.ValidMappingResponse validMappingResponse = userTokenSessionService.isValidUserTokenSessionMapping(userTokenSession);
        if (validMappingResponse.isValid()) {

            LOGGER.info("User " + username + " has valid token."+validMappingResponse.getUserTokenSession());
            responseHeaders.add("Message", "Valid Token");
            responseEntity = new ResponseEntity<UserTokenSessionEntity>(validMappingResponse.getUserTokenSession(), responseHeaders, HttpStatus.OK);

        } else {

            LOGGER.info("User " + username + " has invalid token.");
            responseHeaders.add("Message", "Invalid Token");
            responseEntity = new ResponseEntity<UserTokenSessionEntity>(userTokenSession, responseHeaders, HttpStatus.UNAUTHORIZED);
        }

        return responseEntity;
    }

    @ApiOperation(value = "User Logout", response = UserTokenSessionEntity.class)
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserTokenSessionEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<UserTokenSessionEntity> logout(@RequestHeader HttpHeaders httpHeaders, Principal principal) {

        String username = principal.getName();
        UserTokenSessionEntity userTokenSession = buildUserTokenSession(principal, httpHeaders);
        userTokenSessionService.deleteUserTokenSessionEntity(userTokenSession);

        LOGGER.info("User "+username+" successfully logged out.");

        return new ResponseEntity<UserTokenSessionEntity>(HttpStatus.OK);
    }

    /**
     * Build Token session using {@link Principal} and {@link HttpHeaders}
     * @param principal
     * @param httpHeaders
     * @return TokenSession
     */
    private UserTokenSessionEntity buildUserTokenSession(Principal principal, HttpHeaders httpHeaders) {

        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) ((OAuth2Authentication) principal).getDetails();
        String tokenValue = oAuth2AuthenticationDetails.getTokenValue();
        String username = principal.getName();
        String [] sessionId = new String[1];

        if (Objects.nonNull(httpHeaders.get("cookie"))) {
            sessionId = httpHeaders.get("cookie").get(0).split(";");
        }else {
            LOGGER.info("User " + username + " cookie not found. JSessionId not set.");

            /**
             * Swagger2 does not support cookie for that putting default sessssion id.
             */
            sessionId[0] = "JSEESION-ID";
        }

        UserTokenSessionEntity userTokenSession = new UserTokenSessionEntity(username, tokenValue, sessionId[0], tokenExpiryTime);
        return userTokenSession;
    }
}
