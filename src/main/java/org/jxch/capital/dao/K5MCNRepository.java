package org.jxch.capital.dao;

import org.jxch.capital.domain.po.K5MCN;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface K5MCNRepository extends JpaRepository<K5MCN, K5MCN.K5MCNId> {

    List<K5MCN> findAllByIdCodeAndIdTimeBetween(String code, Date start, Date end);

}
