package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.OtpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOtpRequestRepository extends JpaRepository<OtpRequest, Integer> {
    OtpRequest findByEmail(String email);
}
