package com.authentication.repository;

import com.authentication.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author TungBoom
 */
@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {

	/**
	 *
	 * @param username
	 * @return @{@link UsersEntity}
	 */
	UsersEntity findOneByUsername(String username);
}
