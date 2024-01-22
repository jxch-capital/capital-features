package org.jxch.capital.dao;

import org.jxch.capital.domain.po.UserSubscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriberRepository extends JpaRepository<UserSubscriber, Long> {

    List<UserSubscriber> findAllByUserId(Long userId);

}
