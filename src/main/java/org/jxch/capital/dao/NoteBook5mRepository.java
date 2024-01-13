package org.jxch.capital.dao;

import org.jxch.capital.domain.po.NoteBook5m;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NoteBook5mRepository extends JpaRepository<NoteBook5m, Long> {

    List<NoteBook5m> findAllByCode(String code);

    List<NoteBook5m> findAllByCodeAndDate(String code, LocalDate date);

    void deleteAllByCodeAndDate(String code, LocalDate date);

}
