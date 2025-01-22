package com.looptracker.looptracker.repository;
import com.looptracker.looptracker.dto.response.RiderInforResponse;
import com.looptracker.looptracker.entity.RiderInfor;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRiderInforRepository extends JpaRepository<RiderInfor, Integer> {
    boolean existsByCitizenIdNumber(String citizenIdNumber);
    boolean existsByLicenseNumber(String licenseNumber);

    @Query(value = "SELECT ri.id, ri.license_number, ri.citizen_id_number, ri.rider_status, " +
            "ri.address, ri.cccd_front, ri.cccd_back, ri.gplx_front, ri.gplx_back, " +
            "u.create_at AS create_at, COUNT(ta.id) AS total_trips " +
            "FROM rider_infor ri " +
            "JOIN users u ON ri.user_id = u.id " +
            "LEFT JOIN tour_assignments ta ON u.id = ta.easy_rider " +
            "WHERE ri.user_id = :userId " +
            "GROUP BY ri.id", nativeQuery = true)
    Tuple findRiderInfoByUserId(@Param("userId") String userId);

    RiderInfor findByUser_Id(String userId);
}
