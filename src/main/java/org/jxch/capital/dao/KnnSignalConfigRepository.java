package org.jxch.capital.dao;

import org.jxch.capital.domain.po.KnnSignalConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnnSignalConfigRepository  extends JpaRepository<KnnSignalConfig, Long> {

    KnnSignalConfig findByName(String name);

}
