package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.TourAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITourAssignmentRepository extends JpaRepository<TourAssignment, String> {
    boolean existsByPassenger_Id(Long id);
    Page<TourAssignment> findAllByTourInstances_Id(Long tourInstanceId, Pageable pageable);
}
