package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident,Long> {
    @Query("SELECT i FROM Incident i " +
            "JOIN TourAssignment ta ON ta.id = i.tourAssignments  " +
            "JOIN TourInstance ti ON ta.tourInstances = ti " +
            "WHERE ti.id = :tourPackageId")
    List<Incident> findAllByTourPackageId(@Param("tourPackageId") String tourPackageId);
}
