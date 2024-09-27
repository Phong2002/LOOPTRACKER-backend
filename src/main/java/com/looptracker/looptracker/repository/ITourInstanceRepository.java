package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.TourInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITourInstanceRepository extends JpaRepository<TourInstance, Long> {
}
