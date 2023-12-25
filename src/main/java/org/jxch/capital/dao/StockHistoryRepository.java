package org.jxch.capital.dao;

import org.jxch.capital.domain.po.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {

    @Query("select h from stock_history h where h.stockPoolId = ?1")
    List<StockHistory> findByStockPoolId(Long id);

}
