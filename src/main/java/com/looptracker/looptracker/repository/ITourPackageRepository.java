package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITourPackageRepository extends JpaRepository<TourPackage, String> {
}