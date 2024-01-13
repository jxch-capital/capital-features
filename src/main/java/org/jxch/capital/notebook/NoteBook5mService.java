package org.jxch.capital.notebook;

import org.jxch.capital.domain.dto.NoteBook5mDto;

import java.time.LocalDate;
import java.util.List;

public interface NoteBook5mService {

    List<NoteBook5mDto> findAll();

    List<NoteBook5mDto> findAllByCode(String code);

    List<NoteBook5mDto> findAllByCodeAndDate(String code, LocalDate date);

    void delById(Long id);

    void delById(List<Long> ids);

    void delByCodeAndDate(String code, LocalDate date);

    Integer save(List<NoteBook5mDto> dtoList);

    default void backup() {
        throw new UnsupportedOperationException("默认不支持备份操作");
    }

}
