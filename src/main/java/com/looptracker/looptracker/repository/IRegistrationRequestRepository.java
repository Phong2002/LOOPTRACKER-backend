package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistrationRequestRepository extends JpaRepository<RegistrationRequest, Integer> {

}
