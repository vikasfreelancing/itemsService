package com.viku.itemService.repository;

import com.viku.itemService.dao.FoundItem;
import com.viku.itemService.dao.LostItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoundItemRepository extends CrudRepository<FoundItem, Long> {
    FoundItem save(FoundItem foundItem);
    List<FoundItem> findAll();
}
