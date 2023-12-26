package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.DistanceService;
import org.springframework.stereotype.Service;
import smile.math.distance.Distance;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistanceServiceImpl implements DistanceService<KLine> {
    private final KLineMapper kLineMapper;


    @SuppressWarnings("unchecked")
    private Distance<double[]> getDistance(@NonNull Supplier<Distance<?>> distance) {
        return (Distance<double[]>) distance.get();
    }

    @Override
    public boolean support(@NonNull List<KLine> a, @NonNull List<KLine> b, @NonNull Supplier<Distance<?>> distance) {
        try {
            if (a.isEmpty() || b.isEmpty()) {
                throw new IllegalArgumentException("带计算距离的入参长度不能为空");
            }

            if (Objects.isNull(getDistance(distance))) {
                throw new IllegalArgumentException("计算距离的实例不能为空");
            }
            return true;
        } catch (ClassCastException exception) {
            log.info("Distance 接口只支持 double[] 类型的计算");
            return false;
        }
    }

    @Override
    public double distance(@NonNull List<KLine> a, @NonNull List<KLine> b, @NonNull Supplier<Distance<?>> distance) {
        if (support(a, b, distance)) {
            Distance<double[]> dist = getDistance(distance);

            return (dist.d(kLineMapper.toCloseArr(a), kLineMapper.toCloseArr(b))
                    + dist.d(kLineMapper.toOpenArr(a), kLineMapper.toOpenArr(b))
                    + dist.d(kLineMapper.toHighArr(a), kLineMapper.toHighArr(b))
                    + dist.d(kLineMapper.toLowArr(a), kLineMapper.toLowArr(b))) / 4;
        } else {
            throw new IllegalArgumentException("不支持的计算方式");
        }
    }

}
