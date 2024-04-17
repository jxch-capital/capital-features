package org.jxch.capital.dao;

import org.jxch.capital.domain.po.KHash5Long5MCN;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KHash5Long5MCNRepository extends JpaRepository<KHash5Long5MCN, KHash5Long5MCN.KHash5MCNId> {

    List<KHash5Long5MCN> findAllByHash5Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash5Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash10Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash10Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash16Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash16Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash24Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash24Between(long from, long to, Pageable pageable);

    List<KHash5Long5MCN> findAllByHash48Between(long from, long to);

    Page<KHash5Long5MCN> findAllByHash48Between(long from, long to, Pageable pageable);

}
