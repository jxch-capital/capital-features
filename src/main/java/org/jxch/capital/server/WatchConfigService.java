package org.jxch.capital.server;

import org.jxch.capital.domain.dto.WatchConfigDto;

import java.util.List;

public interface WatchConfigService {

    List<WatchConfigDto> findAll();

    WatchConfigDto findById(Long id);

    List<WatchConfigDto> findById(List<Long> ids);

    List<WatchConfigDto> findByUserId(Long userid);

    List<WatchConfigDto> findByWatchName(String watchName);

    boolean userHasWatch(Long userid, String watchName);

    void del(Long id);

    void del(List<Long> ids);

    Integer save(List<WatchConfigDto> dtoList);

}
