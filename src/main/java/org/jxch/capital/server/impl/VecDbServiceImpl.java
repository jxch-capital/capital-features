package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.http.vec.VecDbApi;
import org.jxch.capital.http.vec.dto.VecParam;
import org.jxch.capital.http.vec.dto.VecRes;
import org.jxch.capital.server.VecService;
import org.springframework.stereotype.Service;

@Slf4j
@Service("vecDbService")
@RequiredArgsConstructor
public class VecDbServiceImpl implements VecService {
    private final VecDbApi vecDbApi;

    @Override
    public VecRes search(VecParam param) {
        return vecDbApi.search(param);
    }

}
