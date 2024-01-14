package org.jxch.capital.dao;

import org.jxch.capital.domain.po.WatchConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchConfigRepository extends JpaRepository<WatchConfig, Long> {

    List<WatchConfig> findAllByUserId(Long userId);

    List<WatchConfig> findAllByWatchName(String watchName);

    List<WatchConfig> findAllByUserIdAndWatchName(Long userId, String watchName);

}
