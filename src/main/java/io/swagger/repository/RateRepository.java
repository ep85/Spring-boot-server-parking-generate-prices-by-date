package io.swagger.repository;

import io.swagger.model.ItemPrice;
import io.swagger.model.RateItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RateRepository extends JpaRepository<RateItem, Integer> {

    @Query(value = "INSERT INTO rates (days, times, tz, price) VALUES (:days, :times, :tz, :price)", nativeQuery = true)
    @Modifying
    void insertRateItem(@Param("days") String days, @Param("times") String times, @Param("tz") String tz, @Param("price") Long price);

    @Query(value = "SELECT * FROM rates", nativeQuery = true)
    List<RateItem> getAllItems();

    @Query(value = "SELECT * FROM rates r WHERE r.time >= :start AND r.time <= :end", nativeQuery = true)
    ItemPrice getByDateRange(@Param("start") String start, @Param("end") String end) ;
}
