package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.TourAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITourAssignmentRepository extends JpaRepository<TourAssignment, String> {
}
