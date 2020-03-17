package com.viku.itemService.controller;

import com.viku.itemService.dao.Item;
import com.viku.itemService.service.ItemPullService;
import com.viku.itemService.service.ItemPushService;
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
    private ItemPushService itemPushService;
    @Autowired
    private ItemPullService itemPullService;

    @GetMapping("/health")
    public String health() {
        return "I am itemService up and running";
    }

    @PostMapping("/saveItem")
    public Item saveItem(@RequestBody Item item) {
        log.info("Requet recived for saving item with request : {}", item);
        Item savedItem = itemPushService.saveitemAndPushTokafka(item);
        log.info("Item : {} saved sucessfully ", savedItem);
        return savedItem;
    }

    @GetMapping("/items")
    public List<Item> getItems() {
        log.info("Requet recived for get Items");
        List<Item> items = itemPullService.getItems();
        log.info("Returning following items : {}", items);
        return items;
    }
}
