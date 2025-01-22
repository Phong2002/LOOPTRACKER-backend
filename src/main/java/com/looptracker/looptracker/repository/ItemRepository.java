package com.looptracker.looptracker.repository;

import com.looptracker.looptracker.entity.Item;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    @Query(value = "SELECT " +
            "    i.*, " +
            "    im_totals.total_import, " +
            "    im_totals.total_export, " +
            "    ai_totals.total_borrowing " +
            "FROM " +
            "    `items` i " +
            "    LEFT JOIN ( " +
            "        SELECT  " +
            "            item, " +
            "            SUM(CASE WHEN type = 'ADDED' THEN quantity ELSE 0 END) AS total_import, " +
            "            SUM(CASE WHEN type = 'REMOVED' THEN quantity ELSE 0 END) AS total_export " +
            "        FROM  " +
            "            item_movements " +
            "        GROUP BY  " +
            "            item " +
            "    ) AS im_totals ON i.id = im_totals.item " +
            "    LEFT JOIN ( " +
            "        SELECT  " +
            "            item, " +
            "            SUM(CASE WHEN status = 'BORROWED' THEN COALESCE(quantity, 0) ELSE 0 END) AS total_borrowing " +
            "        FROM  " +
            "            assignment_items " +
            "        GROUP BY  " +
            "            item " +
            "    ) AS ai_totals ON i.id = ai_totals.item ",nativeQuery = true)
    Page<Tuple> findAllItems(Pageable pageable);
}
