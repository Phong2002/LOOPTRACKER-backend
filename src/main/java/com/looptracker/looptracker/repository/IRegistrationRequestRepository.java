package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.RegistrationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRegistrationRequestRepository extends JpaRepository<RegistrationRequest, String> {
    boolean existsByCitizenIdNumber(String citizenIdNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    RegistrationRequest findByEmail(String email);
    @Query(value = "SELECT rq.* FROM `registration_request` rq WHERE rq.`status` in (:status) " +
            "AND (:search IS NULL OR CONCAT(rq.first_name, ' ', rq.last_name) LIKE CONCAT('%', :search, '%')) ",nativeQuery = true)
    Page<RegistrationRequest> findAllByStatusIn(@Param("search") String search,
                                                @Param("status") List<String> status,
                                                Pageable pageable);

    @Query(value = "SELECT COUNT(*) AS total FROM  registration_request rq WHERE rq.`status` = \"PENDING\"",nativeQuery = true)
    Integer countPending();
}
