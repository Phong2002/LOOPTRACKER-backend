package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.TourPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITourPackageRepository extends JpaRepository<TourPackage, String> {
    @Query(value = "SELECT * FROM tour_packages tp where(:search IS NULL OR tp.tour_name LIKE concat('%',:search,'%'))" +
            "AND (:day IS NULL OR tp.day = :day )" +
            "AND (:minPrice IS NULL OR tp.price >= :minPrice )" +
            "AND (:maxPrice IS NULL OR tp.price <= :maxPrice )",nativeQuery = true)
    Page<TourPackage> findAllAndFilter(@Param("search") String search,
                                   @Param("day") Integer day,
                                   @Param("minPrice") Long minPrice,
                                   @Param("maxPrice") Long maxPrice,
                                   Pageable pageable);
}