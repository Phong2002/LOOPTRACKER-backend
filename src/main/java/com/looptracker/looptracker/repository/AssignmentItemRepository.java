package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.AssignmentItem;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentItemRepository extends JpaRepository<AssignmentItem, String> {
    @Query(value = "SELECT " +
            " (CASE WHEN ta.easy_rider IS NULL THEN 'Khách tự lái' ELSE CONCAT(u.last_name, ' ', u.first_name) END) AS responsible_person, " +
            "  i.NAME AS item_name, " +
            "  ai.quantity as quantity " +
            "FROM " +
            " assignment_items ai " +
            " JOIN items i ON i.id = ai.item " +
            " JOIN tour_assignments ta ON ai.tour_assignments = ta.id " +
            " JOIN tour_instances ti ON ta.tour_instances = ti.id  " +
            " LEFT JOIN users u ON ta.easy_rider = u.id " +
            "WHERE " +
            " ai.`status` = 'BORROWED' " +
            " AND ti.id = :tourInstanceId; ",nativeQuery = true)
    List<Tuple> findAllByTourInstance(@Param("tourInstanceId") Long tourInstanceId);
}
