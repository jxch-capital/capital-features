package org.jxch.capital.khash.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.dao.KHash5Long5MCNRepository;
import org.jxch.capital.domain.convert.KHash5Long5MCNMapper;
import org.jxch.capital.domain.dto.KHash5Long5MCNDto;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.khash.*;
import org.jxch.capital.utils.NumberU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class KHash5Long5MCNServiceImpl implements KHash5Long5MCNService {
    private final KHash5Long5MCNRepository kHash5Long5MCNRepository;
    private final KHash5Long5MCNMapper kHash5Long5MCNMapper;

    @Override
    public Integer saveByReader(@NotNull KReader kReader, @NotNull KHashAgg2List<KHash5Long5MCNDto> kHashAgg2List) {
        return kHash5Long5MCNRepository.saveAll(kHash5Long5MCNMapper.toKHash5Long5MCN(kHashAgg2List.agg(kReader.read()))).size();
    }

    public Integer saveByBaoStockCsvFilesPath(@NotNull Path csvFilesPath) {
        TimeInterval timer = DateUtil.timer();
        LongAdder adder = new LongAdder();
        List<File> csvFiles = Arrays.stream(Objects.requireNonNull(csvFilesPath.toFile().listFiles())).toList();
        return csvFiles.parallelStream().map(file -> {
            KHashAgg2List<KHash5Long5MCNDto> kHashAgg2List = new DailyGridKHashKLinesAgg2HashSkips().setCode(file.getName().split("_")[0]);
            Integer succeeded = saveByReader(new BaoStockCSVKReader().setCsvFile(file), kHashAgg2List);
            adder.add(1);
            log.info("[{}/{}] [{}s.|{}m.] {} -{}", adder.sum(), csvFiles.size(), timer.intervalSecond(), timer.intervalMinute(), succeeded, file.getName());
            return succeeded;
        }).reduce(0, Integer::sum);
    }

    @Override
    public List<KHash5Long5MCNDto> findHash5ByRealHash5InSort(@NotNull String realHash5) {
        if (realHash5.length() <= 5) {
            long from = NumberU.fillLong(realHash5, 5);
            long to = NumberU.incFillLong(realHash5, 5);
            return kHash5Long5MCNMapper.toKHash5Long5MCNDto(kHash5Long5MCNRepository.findAllByHash5l5s10Between(from, to));
        } else {
            throw new UnsupportedOperationException("不支持超过5位的Hash5");
        }
    }

    @Override
    public Page<KHash5Long5MCNDto> findHash5ByRealHash5InSort(@NotNull String realHash5, Pageable pageable) {
        if (realHash5.length() <= 5) {
            long from = NumberU.fillLong(realHash5, 5);
            long to = NumberU.incFillLong(realHash5, 5);
            return kHash5Long5MCNRepository.findAllByHash5l5s10Between(from, to, pageable).map(kHash5Long5MCNMapper::toKHash5Long5MCNDto);
        } else {
            throw new UnsupportedOperationException("不支持超过5位的Hash5");
        }
    }

    @Override
    public List<KHash5Long5MCNDto> findHash10ByRealHash10InSort(@NotNull String realHash10) {
        if (realHash10.length() <= 10) {
            long from = NumberU.fillLong(realHash10, 10);
            long to = NumberU.incFillLong(realHash10, 10);
            return kHash5Long5MCNMapper.toKHash5Long5MCNDto(kHash5Long5MCNRepository.findAllByHash10l10s5Between(from, to));
        } else {
            throw new UnsupportedOperationException("不支持超过10位的Hash10");
        }
    }

    @Override
    public Page<KHash5Long5MCNDto> findHash10ByRealHash10InSort(@NotNull String realHash10, Pageable pageable) {
        if (realHash10.length() <= 10) {
            long from = NumberU.fillLong(realHash10, 10);
            long to = NumberU.incFillLong(realHash10, 10);
            return kHash5Long5MCNRepository.findAllByHash10l10s5Between(from, to, pageable).map(kHash5Long5MCNMapper::toKHash5Long5MCNDto);
        } else {
            throw new UnsupportedOperationException("不支持超过10位的Hash10");
        }
    }

    @Override
    public List<KHash5Long5MCNDto> findHash16ByRealHash16InSort(@NotNull String realHash16) {
        if (realHash16.length() <= 16) {
            long from = NumberU.fillLong(realHash16, 16);
            long to = NumberU.incFillLong(realHash16, 16);
            return kHash5Long5MCNMapper.toKHash5Long5MCNDto(kHash5Long5MCNRepository.findAllByHash16l16s3Between(from, to));
        } else {
            throw new UnsupportedOperationException("不支持超过16位的Hash16");
        }
    }

    @Override
    public Page<KHash5Long5MCNDto> findHash16ByRealHash16InSort(@NotNull String realHash16, Pageable pageable) {
        if (realHash16.length() <= 16) {
            long from = NumberU.fillLong(realHash16, 16);
            long to = NumberU.incFillLong(realHash16, 16);
            return kHash5Long5MCNRepository.findAllByHash16l16s3Between(from, to, pageable).map(kHash5Long5MCNMapper::toKHash5Long5MCNDto);
        } else {
            throw new UnsupportedOperationException("不支持超过16位的Hash16");
        }
    }

    @Override
    public List<KHash5Long5MCNDto> findHash24ByRealHash24InSort(@NotNull String realHash24) {
        if (realHash24.length() <= 12) {
            long from = NumberU.fillLong(realHash24, 12);
            long to = NumberU.incFillLong(realHash24, 12);
            return kHash5Long5MCNMapper.toKHash5Long5MCNDto(kHash5Long5MCNRepository.findAllByHash24sub1l12s2Between(from, to));
        } else if (realHash24.length() <= 24) {
            long hash1 = Long.parseLong(realHash24.substring(0, 12));
            long from = NumberU.fillLong(realHash24.substring(12), 12);
            long to = NumberU.incFillLong(realHash24.substring(12), 12);
            return kHash5Long5MCNMapper.toKHash5Long5MCNDto(kHash5Long5MCNRepository.findAllByHash24sub1l12s2AndHash24sub2l12s2Between(hash1, from, to));
        } else {
            throw new UnsupportedOperationException("不支持超过24位的Hash24");
        }
    }

    @Override
    public Page<KHash5Long5MCNDto> findHash24ByRealHash24InSort(@NotNull String realHash24, Pageable pageable) {
        if (realHash24.length() <= 12) {
            long from = NumberU.fillLong(realHash24, 12);
            long to = NumberU.incFillLong(realHash24, 12);
            return kHash5Long5MCNRepository.findAllByHash24sub1l12s2Between(from, to, pageable).map(kHash5Long5MCNMapper::toKHash5Long5MCNDto);
        } else if (realHash24.length() <= 24) {
            long hash1 = Long.parseLong(realHash24.substring(0, 12));
            long from = NumberU.fillLong(realHash24.substring(12), 12);
            long to = NumberU.incFillLong(realHash24.substring(12), 12);
            return kHash5Long5MCNRepository.findAllByHash24sub1l12s2AndHash24sub2l12s2Between(hash1, from, to, pageable).map(kHash5Long5MCNMapper::toKHash5Long5MCNDto);
        } else {
            throw new UnsupportedOperationException("不支持超过24位的Hash24");
        }
    }

    @Override
    public List<KHash5Long5MCNDto> findHash48ByRealHash48InSort(@NotNull String realHash48) {
        if (realHash48.length() <= 12) {
            long from = NumberU.fillLong(realHash48, 12);
            long to = NumberU.incFillLong(realHash48, 12);
            return kHash5Long5MCNMapper.toKHash5Long5MCNDto(kHash5Long5MCNRepository.findAllByHash48sub1l12s1Between(from, to));
        } else if (realHash48.length() <= 24) {
            long hash1 = Long.parseLong(realHash48.substring(0, 12));
            long from = NumberU.fillLong(realHash48.substring(12), 12);
            long to = NumberU.incFillLong(realHash48.substring(12), 12);
            return kHash5Long5MCNMapper.toKHash5Long5MCNDto(kHash5Long5MCNRepository.findAllByHash48sub1l12s1AndHash48sub1l12s1Between(hash1, from, to));
        } else if (realHash48.length() <= 36) {
            long hash1 = Long.parseLong(realHash48.substring(0, 12));
            long hash2 = Long.parseLong(realHash48.substring(12, 24));
            long from = NumberU.fillLong(realHash48.substring(24), 12);
            long to = NumberU.incFillLong(realHash48.substring(24), 12);
            return kHash5Long5MCNMapper.toKHash5Long5MCNDto(kHash5Long5MCNRepository.findAllByHash48sub1l12s1AndHash48sub2l12s1AndHash48sub3l12s1Between(hash1, hash2, from, to));
        } else if (realHash48.length() <= 48) {
            long hash1 = Long.parseLong(realHash48.substring(0, 12));
            long hash2 = Long.parseLong(realHash48.substring(12, 24));
            long hash3 = Long.parseLong(realHash48.substring(24, 36));
            long from = NumberU.fillLong(realHash48.substring(36), 12);
            long to = NumberU.incFillLong(realHash48.substring(36), 12);
            return kHash5Long5MCNMapper.toKHash5Long5MCNDto(kHash5Long5MCNRepository.findAllByHash48sub1l12s1AndHash48sub2l12s1AndHash48sub3l12s1AndHash48sub4l12s1Between(hash1, hash2, hash3, from, to));
        } else {
            throw new UnsupportedOperationException("不支持超过48位的Hash48");
        }
    }

    @Override
    public Page<KHash5Long5MCNDto> findHash48ByRealHash48InSort(@NotNull String realHash48, Pageable pageable) {
        if (realHash48.length() <= 12) {
            long from = NumberU.fillLong(realHash48, 12);
            long to = NumberU.incFillLong(realHash48, 12);
            return kHash5Long5MCNRepository.findAllByHash48sub1l12s1Between(from, to, pageable).map(kHash5Long5MCNMapper::toKHash5Long5MCNDto);
        } else if (realHash48.length() <= 24) {
            long hash1 = Long.parseLong(realHash48.substring(0, 12));
            long from = NumberU.fillLong(realHash48.substring(12), 12);
            long to = NumberU.incFillLong(realHash48.substring(12), 12);
            return kHash5Long5MCNRepository.findAllByHash48sub1l12s1AndHash48sub1l12s1Between(hash1, from, to, pageable).map(kHash5Long5MCNMapper::toKHash5Long5MCNDto);
        } else if (realHash48.length() <= 36) {
            long hash1 = Long.parseLong(realHash48.substring(0, 12));
            long hash2 = Long.parseLong(realHash48.substring(12, 24));
            long from = NumberU.fillLong(realHash48.substring(24), 12);
            long to = NumberU.incFillLong(realHash48.substring(24), 12);
            return kHash5Long5MCNRepository.findAllByHash48sub1l12s1AndHash48sub2l12s1AndHash48sub3l12s1Between(hash1, hash2, from, to, pageable).map(kHash5Long5MCNMapper::toKHash5Long5MCNDto);
        } else if (realHash48.length() <= 48) {
            long hash1 = Long.parseLong(realHash48.substring(0, 12));
            long hash2 = Long.parseLong(realHash48.substring(12, 24));
            long hash3 = Long.parseLong(realHash48.substring(24, 36));
            long from = NumberU.fillLong(realHash48.substring(36), 12);
            long to = NumberU.incFillLong(realHash48.substring(36), 12);
            return kHash5Long5MCNRepository.findAllByHash48sub1l12s1AndHash48sub2l12s1AndHash48sub3l12s1AndHash48sub4l12s1Between(hash1, hash2, hash3, from, to, pageable).map(kHash5Long5MCNMapper::toKHash5Long5MCNDto);
        } else {
            throw new UnsupportedOperationException("不支持超过48位的Hash48");
        }
    }

    @Override
    public List<KHash5Long5MCNDto> findByRealHash48(@NotNull String realHash48, int size) {
        final String finalRealHash4 = realHash48.replaceAll(",", "").replaceAll("\n", "").trim();
        Set<KHash5Long5MCNDto> kHashDtoSet = new HashSet<>(size);
        findAllPageInSize(kHashDtoSet, size, (pageNumber) -> findHash48ByRealHash48InSort(GridNumKHash.hash(1, finalRealHash4), PageRequest.of(pageNumber, size)));
        findAllPageInSize(kHashDtoSet, size, (pageNumber) -> findHash24ByRealHash24InSort(GridNumKHash.hash(2, finalRealHash4), PageRequest.of(pageNumber, size)));
        findAllPageInSize(kHashDtoSet, size, (pageNumber) -> findHash16ByRealHash16InSort(GridNumKHash.hash(3, finalRealHash4), PageRequest.of(pageNumber, size)));
        findAllPageInSize(kHashDtoSet, size, (pageNumber) -> findHash10ByRealHash10InSort(GridNumKHash.hash(5, finalRealHash4), PageRequest.of(pageNumber, size)));
        findAllPageInSize(kHashDtoSet, size, (pageNumber) -> findHash5ByRealHash5InSort(GridNumKHash.hash(10, finalRealHash4), PageRequest.of(pageNumber, size)));
        return kHashDtoSet.stream().toList().subList(0, size);
    }

    @Override
    public List<List<KHash5Long5MCNDto>> findByRealHash48Group(@NotNull String realHash48, int size) {
        final String finalRealHash4 = realHash48.replaceAll(",", "").replaceAll("\n", "").trim();
        Set<KHash5Long5MCNDto> kHashDtoSet = new HashSet<>(size);
        return List.of(
                findAllPageInSizeAndReturnNew(kHashDtoSet, size, (pageNumber) -> findHash48ByRealHash48InSort(GridNumKHash.hash(1, finalRealHash4), PageRequest.of(pageNumber, size))),
                findAllPageInSizeAndReturnNew(kHashDtoSet, size, (pageNumber) -> findHash24ByRealHash24InSort(GridNumKHash.hash(2, finalRealHash4), PageRequest.of(pageNumber, size))),
                findAllPageInSizeAndReturnNew(kHashDtoSet, size, (pageNumber) -> findHash16ByRealHash16InSort(GridNumKHash.hash(3, finalRealHash4), PageRequest.of(pageNumber, size))),
                findAllPageInSizeAndReturnNew(kHashDtoSet, size, (pageNumber) -> findHash10ByRealHash10InSort(GridNumKHash.hash(5, finalRealHash4), PageRequest.of(pageNumber, size))),
                findAllPageInSizeAndReturnNew(kHashDtoSet, size, (pageNumber) -> findHash5ByRealHash5InSort(GridNumKHash.hash(10, finalRealHash4), PageRequest.of(pageNumber, size)))
        );
    }

    @Override
    public List<KHash5Long5MCNDto> findByAgg(List<KLine> kLines, @NotNull KHashAgg2List<KHash5Long5MCNDto> kHashAgg2List, int size) {
        List<KHash5Long5MCNDto> agg = kHashAgg2List.agg(kLines);
        KHash5Long5MCNDto real = agg.get(agg.size() - 1);
        return findByRealHash48(real.getHash48(), size);
    }

    @Override
    public List<List<KHash5Long5MCNDto>> findByAgg2Group(List<KLine> kLines, @NotNull KHashAgg2List<KHash5Long5MCNDto> kHashAgg2List, int size) {
        List<KHash5Long5MCNDto> agg = kHashAgg2List.agg(kLines);
        KHash5Long5MCNDto real = agg.get(agg.size() - 1);
        return findByRealHash48Group(real.getHash48(), size);
    }

    private void findAllPageInSize(@NotNull Set<KHash5Long5MCNDto> kHashDtoSet, int size, Function<Integer, Page<KHash5Long5MCNDto>> pageReader) {
        int pageNumber = 0;
        Page<KHash5Long5MCNDto> page = null;
        while (kHashDtoSet.size() < size && (Objects.isNull(page) || pageNumber < page.getTotalPages())) {
            page = pageReader.apply(pageNumber++);
            kHashDtoSet.addAll(page.toList());
        }
    }

    @NotNull
    private List<KHash5Long5MCNDto> findAllPageInSizeAndReturnNew(@NotNull Set<KHash5Long5MCNDto> kHashDtoSet, int size, Function<Integer, Page<KHash5Long5MCNDto>> pageReader) {
        Set<KHash5Long5MCNDto> newSet = new HashSet<>(size);
        int pageNumber = 0;
        Page<KHash5Long5MCNDto> page = null;
        while (kHashDtoSet.size() < size && (Objects.isNull(page) || pageNumber < page.getTotalPages())) {
            page = pageReader.apply(pageNumber++);
            newSet.addAll(page.toList());
        }

        int need = size - kHashDtoSet.size();
        newSet.removeAll(kHashDtoSet);
        List<KHash5Long5MCNDto> addList = need < newSet.size() && need > 0 ? newSet.stream().toList().subList(0, need) : newSet.stream().toList();
        kHashDtoSet.addAll(addList);

        return addList;
    }

}
