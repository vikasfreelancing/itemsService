package com.viku.itemService.repository;
import com.viku.itemService.dao.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LostItemRepository extends JpaRepository<LostItem, Long> {
    LostItem save(LostItem item);
    List<LostItem> findAll();
    Optional<LostItem> findById(Long id);
}
