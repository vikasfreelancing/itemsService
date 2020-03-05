package com.viku.itemService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viku.itemService.ItemRepository;
import com.viku.itemService.constants.KafkaConstants;
import com.viku.itemService.dao.Item;
import com.viku.itemService.kafkaproducer.MessageProducer;
import net.minidev.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ItemPostService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    @Qualifier("defaultKafkaProducer")
    MessageProducer<String, String> kafkaPushService;

    @Transactional
    public Item saveItemAndPushToKafkaTopic(Item item) throws JsonProcessingException {
     Item response = itemRepository.save(item);
     kafkaPushService.sendMessage(KafkaConstants.SEND_FOR_TRANING,new ObjectMapper().writeValueAsString(item));
     return null;
    }
}
