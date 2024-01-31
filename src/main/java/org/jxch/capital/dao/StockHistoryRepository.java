package org.jxch.capital.dao;

import org.jxch.capital.domain.po.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {

    @Query("select h from stock_history h where h.stockPoolId = ?1")
    List<StockHistory> findByStockPoolId(Long id);

    @Query("select h from stock_history h where h.stockPoolId = ?1 and h.stockCode = ?2")
    List<StockHistory> findByStockPoolIdAndStockCode(Long id, String stockCode);

    @Query("select h from stock_history h where h.stockPoolId = ?1 and h.stockCode in ?2")
    List<StockHistory> findByStockPoolIdAndStockCode(Long id, List<String> stockCodes);

    @Query("select h from stock_history h where h.stockPoolId = ?1 and h.stockCode = ?2 and h.date between ?3 and ?4")
    List<StockHistory> findByStockPoolIdAndStockCode(Long id, String stockCode, Date startDate, Date endDate);

    @Modifying
    @Query("delete from stock_history h where h.stockPoolId = ?1")
    void deleteByStockPoolId(Long id);

    List<StockHistory> findAllByStockCodeAndDateBetween(String stockCode, Date start, Date end);

    void deleteAllByStockPoolIdAndStockCode(Long stockPoolId, String stockCode);

    @Modifying
    @Query("delete from stock_history h where h.stockPoolId = ?1 and h.stockCode in ?2")
    void deleteAllByStockPoolIdAndStockCodes(Long stockPoolId, List<String> codes);

}
