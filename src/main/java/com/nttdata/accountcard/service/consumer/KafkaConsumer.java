package com.nttdata.accountcard.service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.nttdata.accountcard.service.entity.AccountCard;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class KafkaConsumer {

	@KafkaListener(topics = "TOPIC-DEMO", groupId = "group_id")
	public void consume(String message) {
		log.info("Consuming Message {KafkaStringConsumer}:" + message);
	}

	@KafkaListener(topics = "TOPIC-OBJECT", groupId = "group_id")
	public void consumeObject(AccountCard accountCard) {
		log.info("Consuming Message {KafkaStringConsumer2}:" + accountCard.toString());
	}

	@KafkaListener(topics = "TOPIC-OBJECT1", groupId = "group_id")
	public void consumeObject2(AccountCard accountCard) {
		log.info("Consuming Message {KafkaStringConsumer3}:" + accountCard.toString());
	}
}