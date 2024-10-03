package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.ItemMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemMovementRepository extends JpaRepository<ItemMovement, String> {
}
