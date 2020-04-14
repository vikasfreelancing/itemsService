package com.viku.itemService.kafka;


import com.viku.itemService.dao.FoundItem;
import com.viku.itemService.dao.LostItem;
import com.viku.itemService.faceRecognition.FaceRecognition;
import com.viku.itemService.repository.LostItemRepository;
import com.viku.itemService.service.ItemPullService;
import com.viku.itemService.service.ItemPushService;
import lombok.extern.slf4j.Slf4j;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class Consumer {

    @Autowired
    private ItemPullService itemPullService;

    @Autowired
    private ItemPushService itemPushService;
    @Autowired
    private FaceRecognition faceRecognition;
    @KafkaListener(topics = "${cloudkarafka.found_topic}")
    public void processFoundItems(String id,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                                  @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                                  @Header(KafkaHeaders.OFFSET) List<Long> offsets) throws Exception {

        System.out.printf("%s-%d[%d] \"%s\"\n", topics.get(0), partitions.get(0), offsets.get(0), id);
        log.info("Listener Started with message :{}",id);
        FoundItem foundItem = itemPullService.getFoundItem(id);
        log.info("Found Item : {}",foundItem);
        log.info("Loading CNN model");
        faceRecognition.loadModel();
        List<LostItem> lostItems = itemPullService.getLostItems();
        HashMap<String, INDArray> memberEncodingsMap = new HashMap<>();
        lostItems.forEach(lostItem -> {
            if(lostItem.getImages()!=null){
                List<String> imagePaths = Arrays.asList(lostItem.getImages().substring(0,lostItem.getImages().length()-1).split(","));
                imagePaths.forEach(image->{
                    try {
                        faceRecognition.registerNewMember(lostItem.getId()+"",image,memberEncodingsMap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        String foundUser=memberEncodingsMap.size()==0 ? "Unknown user":faceRecognition.whoIs(foundItem.getImages(),memberEncodingsMap);
        if(!foundUser.equalsIgnoreCase("Unknown user")){
            log.info("Found user Id : {}",foundUser);
            LostItem lostItem = itemPullService.getLostItem(foundUser);
            lostItem.setFound(true);
            lostItem.setFoundId(id);
            itemPushService.saveLostItem(lostItem);
        }
    }
}
