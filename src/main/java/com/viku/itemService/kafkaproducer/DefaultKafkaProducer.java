package com.viku.itemService.kafkaproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;

@Component("defaultKafkaProducer")
public class DefaultKafkaProducer<K,V> implements MessageProducer<K, V> {

	@Autowired
	@Qualifier("defaultProducerTemplate")
	private KafkaTemplate<K, V> kafkaTemplate;
	private final static Logger LOGGER = LoggerFactory.getLogger(DefaultKafkaProducer.class);

	@Autowired
    Environment env;

	public ListenableFuture<SendResult<K, V>> sendMessage(String topicName, V message) {
		LOGGER.info("sending message :{}",message);
		return kafkaTemplate.send(topicName, message);
	}

	@Override
	public ListenableFuture<SendResult<K, V>> sendMessage(String topicName, K key, V message) {
		return kafkaTemplate.send(topicName, key, message);

	}

	@Override
	public void sendMessage(String topicName, List<V> messageList){
		messageList.stream().forEach(message->this.sendMessage(topicName,message));
	}

}
