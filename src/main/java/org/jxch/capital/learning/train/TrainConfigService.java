package org.jxch.capital.learning.train;

import org.jxch.capital.domain.dto.TrainConfigDto;

import java.util.Comparator;
import java.util.List;

public interface TrainConfigService {

    List<TrainConfigDto> findAll();

    default List<TrainConfigDto> findAllInSort() {
        return findAll().stream().sorted(Comparator.comparing(TrainConfigDto::getName)).toList();
    }

    TrainConfigDto findById(Long id);

    void del(List<Long> ids);

    Integer save(List<TrainConfigDto> dtoList);

}
