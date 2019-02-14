package com.authentication.repository;

import com.authentication.model.UserTokenSessionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author TungBoom
 */
@Repository
public interface UserTokenSessionRepository extends CrudRepository<UserTokenSessionEntity, Long> {

    /**
     *  Find {@link UserTokenSessionEntity} for the given username.
     * @param username
     * @return @{@link UserTokenSessionEntity}
     */
    UserTokenSessionEntity findOneByUsername(String username);

}