package com.nttdata.accountcard.service.service.impl;

import java.util.Calendar;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.nttdata.accountcard.service.entity.AccountCard;
import com.nttdata.accountcard.service.repository.AccountCardRepository;
import com.nttdata.accountcard.service.service.AccountCardService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class AccountCardServiceImpl implements AccountCardService {
	@Autowired
	AccountCardRepository accountCardRepository;
//https://howtodoinjava.com/kafka/spring-boot-jsonserializer-example/
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	KafkaTemplate<String, AccountCard> kafkaTemplate2;

	@Override
	public Flux<AccountCard> findAll() {
		// TODO Auto-generated method stub
		return accountCardRepository.findAll().sort((o, o1) -> o.getIdAccountCard().compareTo(o1.getIdAccountCard()));
	}

	@Cacheable(value = "AccountCard")
	@Override
	public AccountCard findById(Long idAccountCard) {
		// TODO Auto-generated method stub
		log.info("Go Repository...");
		return accountCardRepository.findById(idAccountCard).blockOptional().orElse(new AccountCard());
	}

	@Override
	public Mono<AccountCard> findByIdMono(Long idAccountCard) {
		// TODO Auto-generated method stub
		log.info("Enviando mensaje...Kafka.....");
		AccountCard accountCard = accountCardRepository.findById(idAccountCard).blockOptional()
				.orElse(new AccountCard());
		this.kafkaTemplate.send("TOPIC-DEMO", "Go-->KAFKA GO TO");
		this.kafkaTemplate2.send("TOPIC-OBJECT", accountCard);
		this.kafkaTemplate2.send("TOPIC-OBJECT1", accountCard);
		return accountCardRepository.findById(idAccountCard);
	}

	@Override
	public Mono<AccountCard> save(AccountCard accountCard) {
		Long count = this.findAll().collect(Collectors.counting()).blockOptional().get();
		Long idAccountCard;
		Long nsecuencia = null;
		if (count != null) {
			if (count <= 0) {
				idAccountCard = Long.valueOf(0);
			} else {
				idAccountCard = this.findAll()
						.collect(Collectors.maxBy(Comparator.comparing(AccountCard::getIdAccountCard))).blockOptional()
						.get().get().getIdAccountCard();
			}
			nsecuencia = (this.findAll().filter(e -> e.getIdCard() == accountCard.getIdCard())
					.collect(Collectors.maxBy(Comparator.comparing(AccountCard::getSequence))).blockOptional().get()
					.orElse(new AccountCard())).getSequence();
		} else {
			idAccountCard = Long.valueOf(0);

		}
		if (accountCard.getIsMainAccount()) {
			this.findAll().filter(e -> e.getIdCard() == accountCard.getIdCard()).flatMap(obj -> {
				log.info("obj:" + obj.toString());
				obj.setIsMainAccount(false);
				return this.update(obj);
			}).subscribe();

		}
		accountCard.setSequence((nsecuencia != null ? nsecuencia + 1 : 1));
		accountCard.setCreationDate(Calendar.getInstance().getTime());
		accountCard.setIdAccountCard(idAccountCard + 1);
		return accountCardRepository.insert(accountCard);
	}

	@Override
	public Mono<AccountCard> update(AccountCard accountCard) {
		// TODO Auto-generated method stub
		accountCard.setCreationDate(Calendar.getInstance().getTime());
		return accountCardRepository.save(accountCard);
	}

	@Override
	public Mono<Void> delete(Long idAccountCard) {
		// TODO Auto-generated method stub
		return accountCardRepository.deleteById(idAccountCard);
	}

	@Override
	public Flux<AccountCard> findByIdCredit(Long idCard) {
		// TODO Auto-generated method stub
		return this.findAll().filter(e -> e.getIdCard() == idCard);
	}
}
