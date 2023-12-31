package org.jxch.capital.dao;

import org.jxch.capital.domain.po.StockBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockBaseRepository extends JpaRepository<StockBase, Long> {

    @Query("select s from stock_base s where s.code = ?1")
    StockBase findByCode(String code);

    @Query("select s from stock_base s where s.code in ?1")
    List<StockBase> findByCode(List<String> codes);

}
