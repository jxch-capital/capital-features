package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.KHash5Long5MCNRepository;
import org.jxch.capital.domain.convert.KHash5Long5MCNMapper;
import org.jxch.capital.domain.dto.KHash5Long5MCNDto;
import org.jxch.capital.server.KHash5Long5MCNService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KHash5Long5MCNServiceImpl implements KHash5Long5MCNService {
    private final KHash5Long5MCNRepository kHash5Long5MCNRepository;
    private final KHash5Long5MCNMapper kHash5Long5MCNMapper;

    @Override
    public Integer saveAll(List<KHash5Long5MCNDto> dtoList) {
        return kHash5Long5MCNRepository.saveAll(kHash5Long5MCNMapper.toKHash5Long5MCN(dtoList)).size();
    }



}
