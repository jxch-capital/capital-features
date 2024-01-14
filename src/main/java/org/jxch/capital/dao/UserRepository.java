package org.jxch.capital.dao;

import org.jxch.capital.domain.po.UserConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserConfig, Long> {

    UserConfig findByUsername(String username);

    UserConfig findByEmail(String email);

}
