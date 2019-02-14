package com.authentication.service;

import com.authentication.model.UserTokenSessionEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author TungBoom
 */
public interface UserTokenSessionService {

    /**
     * Check whether there is mapping between oauth token, username and session-id.
     * And the token is not yet expired.
     * @param userTokenSession
     * @return ValidMappingResponse if valid mapping else throw {@link UsernameNotFoundException}
     */
    ValidMappingResponse isValidUserTokenSessionMapping(UserTokenSessionEntity userTokenSession)  throws UsernameNotFoundException;

    /**
     *
     * @param userTokenSession
     * @return token session record from data base.
     */
    UserTokenSessionEntity saveUserTokenSessionMapping(UserTokenSessionEntity userTokenSession);


    /**
     * Class to store isValidUserTokenSessionMapping() response.
     */
    class ValidMappingResponse {

        private boolean valid;
        private UserTokenSessionEntity userTokenSession;

        public ValidMappingResponse() {
        }

        public ValidMappingResponse(boolean valid, UserTokenSessionEntity userTokenSession ) {
            this.valid = valid;
            this.userTokenSession = userTokenSession;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public UserTokenSessionEntity getUserTokenSession() {
            return userTokenSession;
        }

        public void setUserTokenSession(UserTokenSessionEntity userTokenSession) {
            this.userTokenSession = userTokenSession;
        }
    }

}
