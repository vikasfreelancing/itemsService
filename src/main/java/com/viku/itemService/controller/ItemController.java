package com.viku.itemService.controller;

import com.viku.itemService.dao.FoundItem;
import com.viku.itemService.dao.LostItem;
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

    @PostMapping("/lost/save")
    public LostItem saveLostItem(@RequestBody LostItem item) {
        log.info("Requet recived for saving item with request : {}", item);
        LostItem savedItem = itemPushService.saveitemAndPushTokafka(item);
        log.info("Lost Item : {} saved sucessfully ", savedItem);
        return savedItem;
    }

    @GetMapping("/lost/items")
    public List<LostItem> getLostItems() {
        log.info("Requet recived for get Items");
        List<LostItem> items = itemPullService.getItems();
        log.info("Returning following items : {}", items);
        return items;
    }
    @PostMapping("/found/save")
    public FoundItem saveFoundItem(@RequestBody FoundItem foundItem) {
        log.info("Requet recived for saving found item with request : {}", foundItem);
        FoundItem savedItem = itemPushService.saveFoundAndPushTokafka(foundItem);
        log.info("foundItem : {} saved sucessfully ", savedItem);
        return savedItem;
    }

    @GetMapping("/found/items")
    public List<FoundItem> getFoundItems() {
        log.info("Requet recived for get found Items");
        List<FoundItem> items = itemPullService.getFoundItems();
        log.info("Returning following found items : {}", items);
        return items;
    }

}
