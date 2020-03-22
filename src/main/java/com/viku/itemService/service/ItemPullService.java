package com.viku.itemService.service;

import com.viku.itemService.dao.FoundItem;
import com.viku.itemService.dao.LostItem;
import com.viku.itemService.repository.FoundItemRepository;
import com.viku.itemService.repository.LostItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPullService {
    @Autowired
    private LostItemRepository lostItemRepository;
    @Autowired
    private FoundItemRepository foundItemRepository;

    public List<LostItem> getItems() {
        return lostItemRepository.findAll();
    }
    public List<FoundItem> getFoundItems() {
        return foundItemRepository.findAll();
    }
}
