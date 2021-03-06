package com.viku.itemService.service;

import com.viku.itemService.dao.FoundItem;
import com.viku.itemService.dao.LostItem;
import com.viku.itemService.kafka.Consumer;
import com.viku.itemService.kafka.Producer;
import com.viku.itemService.repository.FoundItemRepository;
import com.viku.itemService.repository.LostItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemPushService {

    @Autowired
    Consumer consumer;

    @Autowired
    private Producer kafkaPushService;
    @Autowired
    private LostItemRepository lostItemRepository;
    @Autowired
    private FoundItemRepository foundItemRepository;

    @Value("${cloudkarafka.lost_topic}")
    private String lostItemKafkaTopic;

    @Value("${cloudkarafka.found_topic}")
    private String foundItemKafkaTopic;

    public LostItem saveitemAndPushTokafka(LostItem item){
        LostItem savedItem = lostItemRepository.save(item);
        kafkaPushService.send(savedItem.getId().toString(),lostItemKafkaTopic);
        return savedItem;
    }
    public FoundItem saveFoundAndPushTokafka(FoundItem item) throws IOException {
        FoundItem savedItem = foundItemRepository.save(item);
        kafkaPushService.send(savedItem.getId()+"",foundItemKafkaTopic);
        return savedItem;
    }

    public LostItem saveLostItem(LostItem lostItem){
        return lostItemRepository.save(lostItem);
    }
}
