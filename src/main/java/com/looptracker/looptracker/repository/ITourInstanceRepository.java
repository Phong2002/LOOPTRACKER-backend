package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.TourInstance;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ITourInstanceRepository extends JpaRepository<TourInstance, Long> {

    @Query(value = "SELECT " +
            " ti.id  " +
            "FROM " +
            " tour_instances ti " +
            " JOIN tour_assignments ta ON ti.id = ta.tour_instances  " +
            "WHERE " +
            " ti.`status` IN ('PREPARING','IN_PROGRESS')  " +
            " AND ta.easy_rider = :rider",nativeQuery = true)
    String getTourInstanceIdByRider (@Param("rider") String rider);

    @Query(value = "SELECT " +
            "ti.id AS tour_instance_id," +
            "ti.start_date," +
            "ti.name," +
            "ti.end_date," +
            "tp.`day`," +
            "tp.night,"+
            "ti.status," +
            "tp.id AS tour_package_id," +
            "tp.tour_name," +
            "tg.first_name AS guide_first_name," +
            "tg.last_name AS guide_last_name," +
            "tg.id AS guide_id," +
            "SUM( CASE WHEN ta.easy_rider IS NOT NULL THEN 1 ELSE 0 END ) AS total_rider," +
            "SUM( CASE WHEN ta.passenger IS NOT NULL THEN 1 ELSE 0 END ) AS total_passenger " +
            "FROM "+
            "tour_instances ti " +
            "LEFT JOIN tour_assignments ta ON ti.id = ta.tour_instances " +
            "LEFT JOIN tour_packages tp ON tp.id = ti.tour_package " +
            "LEFT JOIN users tg ON tg.id = ti.tour_guide " +
            "where(:search IS NULL OR ti.name LIKE concat('%',:search,'%')) " +
            "AND (:status IS NULL OR ti.status = :status ) " +
            "AND (:tourPackage IS NULL OR tp.id = :tourPackage) " +
            "GROUP BY " +
            "ti.id",nativeQuery = true)
    Page<Tuple> getAllTourInstances(@Param("search") String search,
                                    @Param("status") String status,
                                    @Param("tourPackage") String tourPackage,
                                    Pageable pageable);
}
