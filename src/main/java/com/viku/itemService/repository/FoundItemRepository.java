package com.viku.itemService.repository;

import com.viku.itemService.dao.FoundItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {
    FoundItem save(FoundItem foundItem);
    List<FoundItem> findAll();
    Optional<FoundItem> findById(Long id);
}