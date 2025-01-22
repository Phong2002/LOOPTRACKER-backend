package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.ItemMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMovementRepository extends JpaRepository<ItemMovement, String> {
    boolean existsByItem_id(String item_id);

    @Query(value = "SELECT im.* FROM item_movements im join items i ON im.item = i.id where i.id = :item_id",nativeQuery = true)
    Page<ItemMovement> findByItemId(@Param("item_id") String item_id, Pageable pageable);
}
