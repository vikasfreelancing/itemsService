package com.viku.itemService.service;

import com.viku.itemService.ItemRepository;
import com.viku.itemService.dao.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPullService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItems() {
        return itemRepository.findAll();
    }
}
