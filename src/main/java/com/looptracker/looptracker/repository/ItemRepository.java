package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}
