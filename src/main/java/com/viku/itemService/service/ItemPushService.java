package com.viku.itemService.service;

import com.viku.itemService.ItemRepository;
import com.viku.itemService.dao.LostItem;
import com.viku.itemService.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ItemPushService {

    @Autowired
    private Producer kafkaPushService;
    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public LostItem saveitemAndPushTokafka(LostItem item){
        LostItem savedItem = itemRepository.save(item);
        kafkaPushService.send(savedItem.getId().toString());
        return savedItem;
    }
}
