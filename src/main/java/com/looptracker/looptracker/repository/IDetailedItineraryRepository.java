package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.DetailedItinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetailedItineraryRepository extends JpaRepository<DetailedItinerary, Integer> {
}
