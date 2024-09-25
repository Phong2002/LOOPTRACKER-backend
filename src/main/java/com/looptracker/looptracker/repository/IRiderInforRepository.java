package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.RiderInfor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRiderInforRepository extends JpaRepository<RiderInfor, Integer> {
}
