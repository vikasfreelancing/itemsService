package com.viku.itemService.repository;
import com.viku.itemService.dao.LostItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LostItemRepository extends MongoRepository<LostItem, String> {
    LostItem save(LostItem item);
    List<LostItem> findAll();
    Optional<LostItem> findById(String id);
}
