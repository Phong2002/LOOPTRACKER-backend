package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.WayPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWayPointRepository extends JpaRepository<WayPoint, String> {

}
