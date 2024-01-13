package org.jxch.capital.notebook.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.NoteBook5mRepository;
import org.jxch.capital.domain.convert.NoteBook5mMapper;
import org.jxch.capital.domain.dto.NoteBook5mDto;
import org.jxch.capital.notebook.NoteBook5mService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteBook5mServiceImpl implements NoteBook5mService {
    private final NoteBook5mMapper noteBook5mMapper;
    private final NoteBook5mRepository noteBook5mRepository;

    @Override
    public List<NoteBook5mDto> findAll() {
        return noteBook5mMapper.toNoteBook5mDto(noteBook5mRepository.findAll());
    }

    @Override
    public List<NoteBook5mDto> findAllByCode(String code) {
        return noteBook5mMapper.toNoteBook5mDto(noteBook5mRepository.findAllByCode(code));
    }

    @Override
    public List<NoteBook5mDto> findAllByCodeAndDate(String code, LocalDate date) {
        return noteBook5mMapper.toNoteBook5mDto(noteBook5mRepository.findAllByCodeAndDate(code, date));
    }

    @Override
    public void delById(Long id) {
        noteBook5mRepository.deleteById(id);
    }

    @Override
    public void delById(List<Long> ids) {
        noteBook5mRepository.deleteAllById(ids);
    }

    @Override
    public void delByCodeAndDate(String code, LocalDate date) {
        noteBook5mRepository.deleteAllByCodeAndDate(code, date);
    }

    @Override
    public Integer save(List<NoteBook5mDto> dtoList) {
        return noteBook5mRepository.saveAllAndFlush(noteBook5mMapper.toNoteBook5m(dtoList)).size();
    }



}
