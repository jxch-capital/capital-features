package org.jxch.capital.khash;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KHash5Long5MCNDto;
import org.jxch.capital.domain.dto.KLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface KHash5Long5MCNService {

    Integer saveByReader(KReader kReader, KHashAgg2List<KHash5Long5MCNDto> kHashAgg2List);

    List<KHash5Long5MCNDto> findHash5ByRealHash5InSort(@NotNull String realHash5);

    Page<KHash5Long5MCNDto> findHash5ByRealHash5InSort(@NotNull String realHash5, Pageable pageable);

    List<KHash5Long5MCNDto> findHash10ByRealHash10InSort(@NotNull String realHash10);

    Page<KHash5Long5MCNDto> findHash10ByRealHash10InSort(@NotNull String realHash10, Pageable pageable);

    List<KHash5Long5MCNDto> findHash16ByRealHash16InSort(@NotNull String realHash16);

    Page<KHash5Long5MCNDto> findHash16ByRealHash16InSort(@NotNull String realHash16, Pageable pageable);

    List<KHash5Long5MCNDto> findHash24ByRealHash24InSort(@NotNull String realHash24);

    Page<KHash5Long5MCNDto> findHash24ByRealHash24InSort(@NotNull String realHash24, Pageable pageable);

    List<KHash5Long5MCNDto> findHash48ByRealHash48InSort(@NotNull String realHash48);

    Page<KHash5Long5MCNDto> findHash48ByRealHash48InSort(@NotNull String realHash48, Pageable pageable);

    // 定量查询
    List<KHash5Long5MCNDto> findByRealHash48(@NotNull String realHash48, int size);

    List<List<KHash5Long5MCNDto>> findByRealHash48Group(@NotNull String realHash48, int size);

    List<KHash5Long5MCNDto> findByAgg(List<KLine> kLines, KHashAgg2List<KHash5Long5MCNDto> kHashAgg2List, int size);

    List<List<KHash5Long5MCNDto>> findByAgg2Group(List<KLine> kLines, KHashAgg2List<KHash5Long5MCNDto> kHashAgg2List, int size);

    // 全集
    default List<KHash5Long5MCNDto> findByRealHash48InSort(String realHash48) {
        return findByRealHash48In5Sort(realHash48);
    }

    default List<KHash5Long5MCNDto> findByRealHash48In5Sort(String realHash48) {
        List<KHash5Long5MCNDto> kHash5Long5MCNDtoList = new ArrayList<>(findByRealHash48In4Sort(realHash48));
        kHash5Long5MCNDtoList.addAll(findHash5ByRealHash5InSort(GridNumKHash.hash(10, realHash48)));
        return kHash5Long5MCNDtoList.stream().distinct().toList();
    }

    default List<KHash5Long5MCNDto> findByRealHash48In4Sort(String realHash48) {
        List<KHash5Long5MCNDto> kHash5Long5MCNDtoList = new ArrayList<>(findByRealHash48In3Sort(realHash48));
        kHash5Long5MCNDtoList.addAll(findHash10ByRealHash10InSort(GridNumKHash.hash(5, realHash48)));
        return kHash5Long5MCNDtoList.stream().distinct().toList();
    }

    default List<KHash5Long5MCNDto> findByRealHash48In3Sort(String realHash48) {
        List<KHash5Long5MCNDto> kHash5Long5MCNDtoList = new ArrayList<>(findByRealHash48In2Sort(realHash48));
        kHash5Long5MCNDtoList.addAll(findHash16ByRealHash16InSort(GridNumKHash.hash(3, realHash48)));
        return kHash5Long5MCNDtoList.stream().distinct().toList();
    }

    default List<KHash5Long5MCNDto> findByRealHash48In2Sort(String realHash48) {
        List<KHash5Long5MCNDto> kHash5Long5MCNDtoList = new ArrayList<>(findByRealHash48In1Sort(realHash48));
        kHash5Long5MCNDtoList.addAll(findHash24ByRealHash24InSort(GridNumKHash.hash(2, realHash48)));
        return kHash5Long5MCNDtoList.stream().distinct().toList();
    }

    default List<KHash5Long5MCNDto> findByRealHash48In1Sort(String realHash48) {
        return findHash48ByRealHash48InSort(realHash48);
    }

    // 分组
    default List<List<KHash5Long5MCNDto>> findByRealHash48InSortGroup(String realHash48) {
        return List.of(
                findHash48ByRealHash48InSort(realHash48),
                findHash24ByRealHash24InSort(GridNumKHash.hash(2, realHash48)),
                findHash16ByRealHash16InSort(GridNumKHash.hash(3, realHash48)),
                findHash10ByRealHash10InSort(GridNumKHash.hash(5, realHash48)),
                findHash5ByRealHash5InSort(GridNumKHash.hash(10, realHash48))
        );
    }

    default List<List<KHash5Long5MCNDto>> findByRealHash48InSort5Group(String realHash48) {
        return findByRealHash48InSortGroup(realHash48);
    }

    default List<List<KHash5Long5MCNDto>> findByRealHash48InSort4Group(String realHash48) {
        return List.of(
                findHash48ByRealHash48InSort(realHash48),
                findHash24ByRealHash24InSort(GridNumKHash.hash(2, realHash48)),
                findHash16ByRealHash16InSort(GridNumKHash.hash(3, realHash48)),
                findHash10ByRealHash10InSort(GridNumKHash.hash(5, realHash48))
        );
    }

    default List<List<KHash5Long5MCNDto>> findByRealHash48InSort3Group(String realHash48) {
        return List.of(
                findHash48ByRealHash48InSort(realHash48),
                findHash24ByRealHash24InSort(GridNumKHash.hash(2, realHash48)),
                findHash16ByRealHash16InSort(GridNumKHash.hash(3, realHash48))
        );
    }

    default List<List<KHash5Long5MCNDto>> findByRealHash48InSort2Group(String realHash48) {
        return List.of(
                findHash48ByRealHash48InSort(realHash48),
                findHash24ByRealHash24InSort(GridNumKHash.hash(2, realHash48))
        );
    }

    default List<List<KHash5Long5MCNDto>> findByRealHash48InSort1Group(String realHash48) {
        return List.of(findHash48ByRealHash48InSort(realHash48));
    }

    // 长度分组
    default List<List<KHash5Long5MCNDto>> findByRealHash48InSortGroupByHashLength(@NotNull String realHash48, int l1, int l2, int l3, int l4) {
        if (realHash48.length() <= l1) {
            return findByRealHash48InSort1Group(realHash48);
        }
        if (realHash48.length() <= l2) {
            return findByRealHash48InSort2Group(realHash48);
        }
        if (realHash48.length() <= l3) {
            return findByRealHash48InSort3Group(realHash48);
        }
        if (realHash48.length() <= l4) {
            return findByRealHash48InSort4Group(realHash48);
        }
        return findByRealHash48InSort5Group(realHash48);
    }

    default List<List<KHash5Long5MCNDto>> findByRealHash48InSortGroupByHashLength(@NotNull String realHash48) {
        return findByRealHash48InSortGroupByHashLength(realHash48, 10, 15, 20, 48);
    }

}
