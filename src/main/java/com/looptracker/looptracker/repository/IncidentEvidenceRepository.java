package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.IncidentEvidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentEvidenceRepository extends JpaRepository<IncidentEvidence,Long> {
}
