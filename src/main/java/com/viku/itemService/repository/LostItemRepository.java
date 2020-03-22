package com.viku.itemService.repository;
import com.viku.itemService.dao.LostItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LostItemRepository extends CrudRepository<LostItem, Long> {
    LostItem save(LostItem item);
    List<LostItem> findAll();
}
