package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistrationRequestRepository extends JpaRepository<RegistrationRequest, String> {
    boolean existsByCitizenIdNumber(String citizenIdNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    RegistrationRequest findByEmail(String email);

}
