package com.bigdata.boot.chapter57.dao;

import com.bigdata.boot.chapter57.domain.User;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

/**
 * Domain repository for {@link User}.
 *
 * @author Phillip Webb
 */
@Component
public interface UserRepository extends Repository<User, Long> {

	User findByUsername(String username);

}