package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.NotificationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationUserRepository extends JpaRepository<NotificationUser,Long> {
    Page<NotificationUser> findAllByUserId(String userId, Pageable pageable);
}
