package com.viku.itemService;

import com.viku.itemService.dao.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Item save(Item item);
    List<Item> findAll();
}
