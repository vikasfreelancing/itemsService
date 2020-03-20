package com.viku.itemService;
import com.viku.itemService.dao.LostItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<LostItem, Long> {
    LostItem save(LostItem item);
    List<LostItem> findAll();
}
