package org.jxch.capital.dao;

import org.jxch.capital.domain.po.AiRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRoleRepository extends JpaRepository<AiRole, Long> {

    AiRole findByName(String name);

}
