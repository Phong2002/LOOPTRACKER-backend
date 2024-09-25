package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);
    Optional<User>  findById(String id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);

}
