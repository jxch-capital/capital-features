package org.jxch.capital.dao;

import org.jxch.capital.domain.po.KHash5Long5MCN;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KHash5Long5MCNRepository extends JpaRepository<KHash5Long5MCN, KHash5Long5MCN.KHash5MCNId> {

    List<KHash5Long5MCN> findAllByHash5l5s10Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash5l5s10Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash10l10s5Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash10l10s5Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash16l16s3Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash16l16s3Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash24sub1l12s2Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash24sub1l12s2Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash24sub2l12s2Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash24sub2l12s2Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash24sub1l12s2AndHash24sub2l12s2Between(long hash24sub1l12s2, long from, long to);

    Page<KHash5Long5MCN> findAllByHash24sub1l12s2AndHash24sub2l12s2Between(long hash24sub1l12s2, long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash48sub1l12s1Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash48sub1l12s1Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash48sub2l12s1Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash48sub2l12s1Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash48sub3l12s1Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash48sub3l12s1Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash48sub4l12s1Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash48sub4l12s1Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash48sub1l12s1AndHash48sub1l12s1Between(long hash48sub1l12s1, long from, long to);

    Page<KHash5Long5MCN> findAllByHash48sub1l12s1AndHash48sub1l12s1Between(long hash48sub1l12s1, long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash48sub1l12s1AndHash48sub2l12s1AndHash48sub3l12s1Between(long hash48sub1l12s1, long hash48sub2l12s1, long from, long to);

    Page<KHash5Long5MCN> findAllByHash48sub1l12s1AndHash48sub2l12s1AndHash48sub3l12s1Between(long hash48sub1l12s1, long hash48sub2l12s1, long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash48sub1l12s1AndHash48sub2l12s1AndHash48sub3l12s1AndHash48sub4l12s1Between(long hash48sub1l12s1, long hash48sub2l12s1, long hash48sub3l12s1, long from, long to);

    Page<KHash5Long5MCN> findAllByHash48sub1l12s1AndHash48sub2l12s1AndHash48sub3l12s1AndHash48sub4l12s1Between(long hash48sub1l12s1, long hash48sub2l12s1, long hash48sub3l12s1, long from, long to, Pageable pageable);
}
