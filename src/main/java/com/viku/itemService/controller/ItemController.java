package com.viku.itemService.controller;

import com.viku.itemService.ItemRepository;
import com.viku.itemService.dao.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/saveItem")
    public Item saveItem(@RequestBody Item item) {
        return itemRepository.saveItem(item);
    }

    @GetMapping("/items")
    public List<Item> getItems() {
        return itemRepository.findAll();
    }


}
