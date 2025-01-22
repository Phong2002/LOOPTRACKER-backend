package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.Passenger;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPassengerRepository extends JpaRepository<Passenger, Long> {
    @Query(value = "SELECT p.*,ta.id as tour_assignment, ti.status as status " +
            "from passengers p " +
            "LEFT JOIN  tour_assignments ta " +
            "on p.id = ta.passenger " +
            "LEFT JOIN tour_instances ti " +
            "on ta.tour_instances = ti.id "
            , nativeQuery = true)
    Page<Tuple> getAllPassengers(Pageable pageable);

    @Query(value = "SELECT p.*, ta.id as tour_assignment, ti.status AS status " +
            "FROM passengers p " +
            "LEFT JOIN tour_assignments ta " +
            "ON p.id = ta.passenger " +
            "LEFT JOIN tour_instances ti " +
            "ON ta.tour_instances = ti.id " +
            "WHERE CONCAT(p.first_name, ' ', p.last_name) LIKE CONCAT('%',:searchTerm, '%') ",
            nativeQuery = true)
    Page<Tuple> findPassengersByFullName(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query(value = "SELECT p.*, " +
            "ta.id as tour_assignment, " +
            "ti.status AS status " +
            "FROM passengers p " +
            "LEFT JOIN tour_assignments ta ON p.id = ta.passenger " +
            "LEFT JOIN tour_instances ti ON ta.tour_instances = ti.id " +
            "WHERE ti.`status` = :status",
            nativeQuery = true)
    Page<Tuple> findPassengersByStatus(@Param("status") String status, Pageable pageable);

    @Query(value = "SELECT " +
            " p.id AS id, " +
            " p.first_name AS passenger_first_name, " +
            " p.last_name AS passenger_last_name, " +
            " p.phone_number AS passenger_phone_number, " +
            " p.email AS passenger_email, " +
            " p.notes AS passenger_notes, " +
            " p.gender AS passenger_gender, " +
            " tp.tour_name AS tour_package_name, " +
            " tp.`day` AS tour_package_day, " +
            " tp.night AS tour_package_night, " +
            " tp.description AS tour_package_description, " +
            " ti.start_date AS start_date, " +
            " ti.end_date AS end_date, " +
            " ti.`status` AS `status`, "+
            " tg.first_name AS guide_first_name, " +
            " tg.last_name AS guide_last_name, " +
            " rd.first_name AS rider_first_name, " +
            " rd.last_name AS rider_last_name  " +
            "FROM " +
            " passengers p " +
            " LEFT JOIN tour_assignments ta ON p.id = ta.passenger " +
            " LEFT JOIN tour_instances ti ON ta.tour_instances = ti.id " +
            " LEFT JOIN tour_packages tp ON ti.tour_package = tp.id " +
            " LEFT JOIN users tg ON ti.tour_guide = tg.id " +
            " LEFT JOIN users rd ON ta.easy_rider = rd.id " +
            "WHERE (:status IS NULL OR ti.`status` = :status) " +
            "AND(:searchTerm IS NULL OR CONCAT(p.first_name, ' ', p.last_name) LIKE CONCAT('%', :searchTerm, '%'))",
            nativeQuery = true)
    Page<Tuple> findPassengersBySearchNameAndStatus(
            @Param("searchTerm") String searchTerm,
            @Param("status") String status,
            Pageable pageable);

    @Query(value = "SELECT " +
            "p.* " +
            "FROM " +
            "passengers p " +
            "LEFT JOIN tour_assignments ta ON p.id = ta.passenger " +
            "WHERE " +
            "ta.id IS NULL",
            nativeQuery = true)
    List<Passenger> findAllPassengerNoTour();
}
