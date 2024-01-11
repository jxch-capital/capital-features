package org.jxch.capital.dao;

import org.jxch.capital.domain.po.KnnSignalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface KnnSignalHistoryRepository extends JpaRepository<KnnSignalHistory, Long> {

    List<KnnSignalHistory> findKnnSignalHistoriesByKnnSignalConfigIdAndCode(Long knnSignalConfigId, String code);

    List<KnnSignalHistory> findKnnSignalHistoriesByKnnSignalConfigId(Long knnSignalConfigId);

    @Query("select k from knn_signal_history k where k.knnSignalConfigId in ?1")
    List<KnnSignalHistory> findAllByKnnSignalConfigId(List<Long> knnSignalConfigIds);

    List<KnnSignalHistory> findKnnSignalHistoriesByKnnSignalConfigIdAndCodeAndDateBetween(Long knnSignalConfigId, String code, Date start, Date end);

    void deleteAllByKnnSignalConfigId(Long knnSignalConfigId);

    void deleteAllByKnnSignalConfigIdAndCode(Long knnSignalConfigId, String code);

    @Modifying
    @Query("delete from knn_signal_history k where k.knnSignalConfigId in ?1")
    void delByConfigId(List<Long> configIds);

    @Modifying
    @Query("delete from knn_signal_history k where k.knnSignalConfigId = ?1 and k.code in ?2")
    void delByConfigIdAndCode(Long configId, List<String> codes);

}
