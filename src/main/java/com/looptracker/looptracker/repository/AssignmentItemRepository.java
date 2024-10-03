package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.AssignmentItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentItemRepository extends JpaRepository<AssignmentItem, String> {
}
