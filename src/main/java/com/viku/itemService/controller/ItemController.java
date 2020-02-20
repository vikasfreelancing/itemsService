package com.viku.itemService.controller;

import com.viku.itemService.ItemRepository;
import com.viku.itemService.dao.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/health")
    public String health(){
        return "I am itemService up and running";
    }

    @PostMapping("/saveItem")
    public Item saveItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }
    @GetMapping("/items")
    public List<Item> getItems() {
        return itemRepository.findAll();
    }
}
