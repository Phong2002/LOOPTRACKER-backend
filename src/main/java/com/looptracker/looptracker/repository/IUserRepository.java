package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User>  findById(String id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT " +
            " u.*  " +
            "FROM " +
            " users u " +
            " JOIN rider_infor rd ON rd.user_id = u.id  " +
            "WHERE " +
            " u.role IN (:roles)  " +
            " AND rd.rider_status = \"READY\"",nativeQuery = true)
    List<User> findByRoleIn(@Param("roles") List<String> roles);

    @Query(value = "SELECT * FROM users u " +
            "where (:searchTerm IS NULL OR " +
            " CONCAT(u.first_name, ' ', u.last_name) " +
            "LIKE CONCAT('%', :searchTerm, '%')) " +
            "OR (:searchTerm IS NULL OR CONCAT(u.last_name, ' ', u.first_name) " +
            "LIKE  CONCAT('%', :searchTerm, '%')) ",
            nativeQuery = true)
    Page<User> findAll(String searchTerm,Pageable pageable);
}
