//package com.viku.itemService.kafka;
//
//
//import com.viku.itemService.dao.FoundItem;
//import com.viku.itemService.dao.LostItem;
//import com.viku.itemService.faceRecognition.FaceRecognition;
//import com.viku.itemService.repository.LostItemRepository;
//import com.viku.itemService.service.ItemPullService;
//import com.viku.itemService.service.ItemPushService;
//import org.nd4j.linalg.api.ndarray.INDArray;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//public class Consumer {
//
//    @Autowired
//    private ItemPullService itemPullService;
//
//    @Autowired
//    private ItemPushService itemPushService;
//    @Autowired
//    private FaceRecognition faceRecognition;
//    @KafkaListener(topics = "5h860wrw-",groupId = "5h860wrw-consumers")
//    public void processfoundItems(String message) throws IOException {
//        FoundItem foundItem = itemPullService.getFoundItem(Long.parseLong(message));
//        List<LostItem> lostItems = itemPullService.getLostItems();
//        HashMap<String, INDArray> memberEncodingsMap = new HashMap<>();
//        lostItems.forEach(lostItem -> {
//            if(lostItem.getImages()!=null){
//                List<String> imagePaths = Arrays.asList(lostItem.getImages().substring(0,lostItem.getImages().length()-1).split(","));
//                imagePaths.forEach(image->{
//                    try {
//                        faceRecognition.registerNewMember(lostItem.getId()+"",image,memberEncodingsMap);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//            }
//        });
//        String foundUser = faceRecognition.whoIs(foundItem.getImages(),memberEncodingsMap);
//        if(!foundUser.equalsIgnoreCase("Unknown user")){
//            Long foundId = Long.parseLong(foundUser);
//            LostItem lostItem = itemPullService.getLostItem(foundId);
//            lostItem.setFound(true);
//            lostItem.setFoundId(Long.parseLong(message));
//            itemPushService.saveLostItem(lostItem);
//        }
//    }
//}
