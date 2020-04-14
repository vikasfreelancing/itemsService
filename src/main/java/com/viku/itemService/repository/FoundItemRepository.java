package com.viku.itemService.repository;

import com.viku.itemService.dao.FoundItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoundItemRepository extends MongoRepository<FoundItem, String> {
    FoundItem save(FoundItem foundItem);
    List<FoundItem> findAll();
    Optional<FoundItem> findById(String id);
}